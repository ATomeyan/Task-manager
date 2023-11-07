package com.manager.service;

import com.manager.model.Task;

import java.util.List;
import java.util.Optional;

/**
 * @author Artur Tomeyan
 * @date 02.11.2023
 */
public interface TaskService {

    Task addTask();

    void editTask();

    boolean deleteTask();

    List<Task> viewAllTasks();

    List<Task> viewAllTasksSortedByDueDate();

    List<Task> viewAllTasksSortedByStatus();

    Optional<Task> findById(String id);
}