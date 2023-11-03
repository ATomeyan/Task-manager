package com.manager;

import com.manager.service.TaskService;
import com.manager.service.TaskServiceImpl;

/**
 * @author Artur Tomeyan
 * @date 01.11.2023
 */
public class Main {
    public static void main(String[] args) {

        TaskService taskService = new TaskServiceImpl();

        taskService.addTask();

//        taskService.editTask();
    }
}