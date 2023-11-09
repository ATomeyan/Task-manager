package com.manager.service;

import com.manager.model.Task;

import java.util.List;
import java.util.Optional;

/**
 * @author Artur Tomeyan
 * @date 02.11.2023
 */
public interface TaskService {

    Task addTask(Task task);

    void editTask();

    boolean deleteTask(String id);

    List<Task> viewAllTasks();

    List<Task> viewAllTasksSortedByDueDate();

    List<Task> viewAllTasksSortedByStatus();

    List<Task> tasksByFilteringStatus(String status);

    Optional<Task> findById(String id);
}