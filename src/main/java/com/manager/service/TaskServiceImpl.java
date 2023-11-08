package com.manager.service;

import com.manager.database.Query;
import com.manager.database.connector.DBConnector;
import com.manager.model.Task;
import com.manager.utils.Helper;

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

        createdTask.setId(Helper.generateId());
        createdTask.setTitle(Helper.getInput("Title: "));
        createdTask.setDescription(Helper.getInput("Description: "));
        createdTask.setStatus(Helper.getInput("Status: "));
        createdTask.setTaskCreatedAt(Timestamp.valueOf(now));

        LocalDate dueDate = LocalDate.parse(Helper.getInput("Due date: "));

        if (validateDate(dueDate))
            createdTask.setDueDate(dueDate);

        saveTask(createdTask);

        return createdTask;
    }

    @Override
    public void editTask() {

        System.out.println("----------------------------------------------------------------------------");
        Optional<Task> taskById = findById(Helper.getInput("Enter the task id to find task: "));

        if (taskById.isPresent()) {

            System.out.println(taskById.get());
            System.out.println("\nEnter updating data.");
            taskById.get().setTitle(Helper.getInput("Title: "));
            taskById.get().setDescription(Helper.getInput("Description: "));

            LocalDate changedDate = LocalDate.parse(Helper.getInput("Due date: "));

            if (validateDate(changedDate))
                taskById.get().setDueDate(changedDate);

            taskById.get().setStatus(Helper.getInput("Status: "));
            taskById.get().setTaskChangedAt(Timestamp.valueOf(LocalDateTime.now()));

            updateTask(taskById.get());
        }
    }

    @Override
    public boolean deleteTask(String id) {

        Optional<Task> taskById = findById(id);

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
    public List<Task> viewAllTasksSortedByDueDate() {
        try (Statement statement = DB_CONNECTOR.connection().createStatement()) {

            List<Task> tasks = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(Query.sortAllTasksByDueDate);

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
    public List<Task> viewAllTasksSortedByStatus() {
        try (Statement statement = DB_CONNECTOR.connection().createStatement()) {

            List<Task> tasks = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(Query.sortAllTasksByStatus);

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
    public List<Task> tasksByFilteringStatus(String status) {

        try (PreparedStatement preparedStatement = DB_CONNECTOR.connection().prepareStatement(Query.allTasksByStatusFilter)) {

            List<Task> tasks = new ArrayList<>();
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();

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
            value.setTaskCreatedAt(task_created_at);
        }
        if (task_changed_at != null) {
            value.setTaskChangedAt(task_changed_at);
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

            preparedStatement.setString(1, updatedTask.getTitle());
            preparedStatement.setString(2, updatedTask.getDescription());
            preparedStatement.setString(3, updatedTask.getStatus());
            preparedStatement.setString(4, String.valueOf(updatedTask.getDueDate()));
            preparedStatement.setTimestamp(5, updatedTask.getTaskCreatedAt());
            preparedStatement.setTimestamp(6, updatedTask.getTaskChangedAt());
            preparedStatement.setString(7, updatedTask.getId());

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
}