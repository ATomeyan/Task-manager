package com.manager.service;

import com.manager.model.Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Artur Tomeyan
 * @date 05.11.2023
 */
public class TaskFileServiceImpl implements TaskFileService {

    @Override
    public void addTasksToFile(Task task) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Task.txt", true))) {

            writer.write("Task ID: " + task.getId() + "\n" +
                    "Title: " + task.getTitle() + "\n" +
                    "Description: " + task.getDescription() + "\n" +
                    "Due date: " + task.getDueDate() + "\n" +
                    "Status: " + task.getStatus() + "\n" +
                    "Task created at: " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(task.getTaskCreatedAt()));

            writer.newLine();
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateTaskFile(List<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Task.txt"))) {

            for (Task task : tasks) {

                writer.write("Task ID: " + task.getId() + "\n" +
                        "Title: " + task.getTitle() + "\n" +
                        "Description: " + task.getDescription() + "\n" +
                        "Status: " + task.getStatus() + "\n" +
                        "Due date: " + task.getDueDate() + "\n" +
                        "Task created at: " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(task.getTaskCreatedAt())
                );

                if (task.getTaskChangedAt() != null)
                    writer.write("\nTask changed at: " + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(task.getTaskChangedAt()));

                writer.newLine();
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}