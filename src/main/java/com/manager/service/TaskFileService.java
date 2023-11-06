package com.manager.service;

import com.manager.model.Task;

import java.util.List;

/**
 * @author Artur Tomeyan
 * @date 05.11.2023
 */
public interface TaskFileService {
    void addTasksToFile(Task task);

    void updateTaskFile(List<Task> task);
}