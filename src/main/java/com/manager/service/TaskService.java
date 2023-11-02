package com.manager.service;

import com.manager.model.Task;

import java.util.List;

/**
 * @author Artur Tomeyan
 * @date 02.11.2023
 */
public interface TaskService {

    void addTask();

    void editTask(String id);

    boolean deleteTask(String id);

    List<Task> viewAllTasks();
}