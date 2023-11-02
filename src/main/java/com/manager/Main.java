package com.manager;

import com.manager.model.Task;
import com.manager.service.TaskService;
import com.manager.service.TaskServiceImpl;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * @author Artur Tomeyan
 * @date 01.11.2023
 */
public class Main {
    public static void main(String[] args) {

//        Scanner scanner = new Scanner(System.in);
//
//        Task task = new Task();
//
//        task.setTitle(scanner.nextLine());
//        task.setDescription(scanner.nextLine());
//        task.setDueDate(LocalDate.parse(scanner.nextLine()));
//        task.setStatus(scanner.nextLine());

        TaskService taskService = new TaskServiceImpl();

        taskService.addTask();
    }
}