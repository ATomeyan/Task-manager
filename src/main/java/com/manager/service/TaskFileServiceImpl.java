package com.manager.service;

import com.manager.model.Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Artur Tomeyan
 * @date 05.11.2023
 */
public class TaskFileServiceImpl implements TaskFileService {

    @Override
    public void addTasksToFile(Task task) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Task.txt", true))) {

            writer.newLine();
            writer.write("Task ID: " + task.getId() + "\n" +
                    "Title: " + task.getTitle() + "\n" +
                    "Description: " + task.getDescription() + "\n" +
                    "Due date: " + task.getDueDate() + "\n" +
                    "Status: " + task.getStatus() + "\n" +
                    "Task created at: " + task.getTaskCreatedAt());

            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}