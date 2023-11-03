package com.manager.service;

import com.manager.database.Query;
import com.manager.database.connector.DBConnector;
import com.manager.model.Task;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Artur Tomeyan
 * @date 02.11.2023
 */
public class TaskServiceImpl implements TaskService {

    private static final DBConnector DB_CONNECTOR = DBConnector.getDbConnector();
    private final LocalDateTime now = LocalDateTime.now();
    private final String addedDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

    @Override
    public void addTask() {

        createTable();

        Task createdTask = new Task();

        createdTask.setId(generateId());
        createdTask.setTitle(getInput("Title: "));
        createdTask.setDescription(getInput("Description: "));
        createdTask.setStatus(getInput("Status: "));

        createdTask.setTaskCreatedAt(now);

        LocalDate dueDate = LocalDate.parse(getInput("Due date: "));

        if (LocalDate.now().isBefore(dueDate))
            createdTask.setDueDate(dueDate);
        else {
            System.out.println("The due date is not valid and the task was rejected. Please input a valid due date.");
            return;
        }

        addTasksToFile(createdTask);

        try (PreparedStatement preparedStatement = DB_CONNECTOR.connection().prepareStatement(Query.insertIntoTable)) {

            preparedStatement.setString(1, createdTask.getId());
            preparedStatement.setString(2, createdTask.getTitle());
            preparedStatement.setString(3, createdTask.getDescription());
            preparedStatement.setString(4, String.valueOf(createdTask.getDueDate()));
            preparedStatement.setString(5, createdTask.getStatus());
            preparedStatement.setString(6, addedDate);
            preparedStatement.setString(7, null);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editTask() {

//        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("Task.txt"))) {
//
//            Map<String, String> tasks = new HashMap<>();
//            String s;
//
//            while ((s = bufferedReader.readLine()) != null){
//                tasks.put(s, s);
//                System.out.println(tasks);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Task byId = findById("76a127e3-2e75-4252-9314-ed8b0981c761");

        System.out.println(byId);
    }

    @Override
    public boolean deleteTask(String id) {
        return false;
    }

    @Override
    public List<Task> viewAllTasks() {
        return null;
    }

    private Task findById(String id) {

        try (PreparedStatement preparedStatement = DB_CONNECTOR.connection().prepareStatement(Query.findByID)) {

            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Task(
                        resultSet.getString("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("due_date").toLocalDateTime().toLocalDate(),
                        resultSet.getTimestamp("task_created_at").toLocalDateTime(),
                        resultSet.getTimestamp("task_changed_at").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void createTable() {
        try (Statement statement = DB_CONNECTOR.connection().createStatement()) {

            statement.execute(Query.createTable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getInput(String prompt) {
        System.out.print(prompt);

        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void addTasksToFile(Task task) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Task.txt", true))) {

            writer.write("Added new task at " + addedDate);
            writer.newLine();
            writer.write(
                    "Task ID: " + task.getId() + "\n" +
                            "Title: " + task.getTitle() + "\n" +
                            "Description: " + task.getDescription() + "\n" +
                            "Due date: " + task.getDueDate() + "\n" +
                            "Status: " + task.getStatus());

            writer.newLine();
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }
}