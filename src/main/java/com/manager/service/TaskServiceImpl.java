package com.manager.service;

import com.manager.model.Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * @author Artur Tomeyan
 * @date 02.11.2023
 */
public class TaskServiceImpl implements TaskService {

    @Override
    public void addTask() {

        Task createdTask = new Task();

            createdTask.setId(generateId());
            createdTask.setTitle(getInput("Title: "));
            createdTask.setDescription(getInput("Description: "));
            createdTask.setStatus(getInput("Status: "));

            LocalDate dueDate = LocalDate.parse(getInput("Due date: "));

            if (LocalDate.now().isBefore(dueDate))
                createdTask.setDueDate(dueDate);
            else {
                System.out.println("The due date is not valid and the task was rejected. Please input a valid due date.");
                return;
            }

        addTasksToFile(createdTask);
    }

    @Override
    public void editTask(String id) {
    }

    @Override
    public boolean deleteTask(String id) {
        return false;
    }

    @Override
    public List<Task> viewAllTasks() {
        return null;
    }

    private String getInput(String prompt) {
        System.out.print(prompt);

        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void addTasksToFile(Task task) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Task.txt", true))) {

            LocalDateTime now = LocalDateTime.now();
            String addedDate = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));

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