package com.manager.service;

import com.manager.model.Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Artur Tomeyan
 * @date 05.11.2023
 */
public class TaskFileServiceImpl implements TaskFileService {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public void addTasksToFile(Task task) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Task.txt", true))) {

            writer.write("\nTask ID: " + task.getId() + "\n" +
                    "Title: " + task.getTitle() + "\n" +
                    "Description: " + task.getDescription() + "\n" +
                    "Due date: " + task.getDueDate() + "\n" +
                    "Status: " + task.getStatus() + "\n" +
                    "Task created at: " + task.getTaskCreatedAt().format(dateTimeFormatter));

            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateTaskFile(List<Task> task) {
        // TODO document why this method is empty
    }
}