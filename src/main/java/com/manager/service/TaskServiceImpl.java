package com.manager.service;

import com.manager.database.Query;
import com.manager.database.connector.DBConnector;
import com.manager.model.Task;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Artur Tomeyan
 * @date 02.11.2023
 */
public class TaskServiceImpl implements TaskService {

    private static final DBConnector DB_CONNECTOR = DBConnector.getDbConnector();
    private final LocalDateTime now = LocalDateTime.now();

    @Override
    public Task addTask() {

        createTable();

        Task createdTask = new Task();

        createdTask.setId(generateId());
        createdTask.setTitle(getInput("Title: "));
        createdTask.setDescription(getInput("Description: "));
        createdTask.setStatus(getInput("Status: "));
        createdTask.setTaskCreatedAt(now);

        LocalDate dueDate = LocalDate.parse(getInput("Due date: "));

        if (validateDate(dueDate))
            createdTask.setDueDate(dueDate);

        saveTask(createdTask);

        return createdTask;
    }

    @Override
    public void editTask() {

        Optional<Task> taskById = findById(getInput("Enter the task id to find task: "));

        System.out.println(taskById);

        if (taskById.isPresent()) {
            taskById.get().setTitle(getInput("Title: "));
            taskById.get().setDescription(getInput("Description: "));

            LocalDate changedDate = LocalDate.parse(getInput("Due date: "));

            if (validateDate(changedDate))
                taskById.get().setDueDate(changedDate);

            taskById.get().setStatus(getInput("Status: "));
            taskById.get().setTaskChangedAt(LocalDateTime.now());

            updateTask(taskById.get());
        }
    }

    @Override
    public boolean deleteTask() {

        Optional<Task> taskById = findById(getInput("Enter task id: "));

        if (taskById.isPresent()) {
            try (PreparedStatement preparedStatement = DB_CONNECTOR.connection().prepareStatement(Query.delete)) {

                preparedStatement.setString(1, taskById.get().getId());
                preparedStatement.executeUpdate();

                return true;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public List<Task> viewAllTasks() {

        try (Statement statement = DB_CONNECTOR.connection().createStatement()) {

            List<Task> tasks = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(Query.viewAllTasks);


            while (resultSet.next()) {
                Task task = extractTaskFromResultSet(resultSet);
                tasks.add(task);
            }

            return tasks;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Collections.emptyList();
    }

    @Override
    public Optional<Task> findById(String id) {

        try (PreparedStatement preparedStatement = DB_CONNECTOR.connection().prepareStatement(Query.findByID)) {

            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                return Optional.of(extractTaskFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Optional.empty();
    }

    private Task extractTaskFromResultSet(ResultSet resultSet) throws SQLException {
        Task value = new Task();
        value.setId(resultSet.getString("id"));
        value.setTitle(resultSet.getString("title"));
        value.setDescription(resultSet.getString("description"));
        value.setStatus(resultSet.getString("status"));
        value.setDueDate(resultSet.getDate("due_date").toLocalDate());

        Timestamp task_created_at = resultSet.getTimestamp("task_created_at");
        Timestamp task_changed_at = resultSet.getTimestamp("task_changed_at");

        if (task_created_at != null) {
            value.setTaskCreatedAt(task_created_at.toLocalDateTime());
        }
        if (task_changed_at != null) {
            value.setTaskChangedAt(task_changed_at.toLocalDateTime());
        }

        return value;
    }

    private void saveTask(Task createdTask) {

        try (PreparedStatement preparedStatement = DB_CONNECTOR.connection().prepareStatement(Query.insertIntoTable)) {

            preparedStatement.setString(1, createdTask.getId());
            preparedStatement.setString(2, createdTask.getTitle());
            preparedStatement.setString(3, createdTask.getDescription());
            preparedStatement.setString(4, String.valueOf(createdTask.getDueDate()));
            preparedStatement.setString(5, createdTask.getStatus());
            preparedStatement.setString(6, createdTask.getTaskCreatedAt().toString());

            preparedStatement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateTask(Task updatedTask) {
        try (PreparedStatement preparedStatement = DB_CONNECTOR.connection().prepareStatement(Query.updateTaskById)) {

            preparedStatement.setString(1, updatedTask.getId());
            preparedStatement.setString(2, updatedTask.getTitle());
            preparedStatement.setString(3, updatedTask.getDescription());
            preparedStatement.setString(4, String.valueOf(updatedTask.getDueDate()));
            preparedStatement.setString(5, updatedTask.getStatus());
            preparedStatement.setString(6, updatedTask.getDueDate().toString());
            preparedStatement.setString(7, updatedTask.getTaskChangedAt().toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean validateDate(LocalDate date) {
        if (LocalDate.now().isBefore(date))
            return true;
        else {
            System.out.println("The due date is not valid and the task was rejected. Please input a valid due date.");
            return false;
        }
    }

    private void createTable() {
        try (Statement statement = DB_CONNECTOR.connection().createStatement()) {

            statement.execute(Query.createTable);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private String getInput(String prompt) {
        System.out.print(prompt);

        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private String generateId() {
        return RandomStringUtils.randomAlphanumeric(8);
    }
}