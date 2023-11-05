package com.manager.ui;

import com.manager.model.Task;
import com.manager.service.TaskFileService;
import com.manager.service.TaskFileServiceImpl;
import com.manager.service.TaskService;
import com.manager.service.TaskServiceImpl;

import java.util.Scanner;

/**
 * @author Artur Tomeyan
 * @date 01.11.2023
 */
public class ConsoleUI {

    private final Scanner scanner = new Scanner(System.in);
    private final TaskService taskService;
    private final TaskFileService taskFileService;

    public ConsoleUI() {
        taskService = new TaskServiceImpl();
        taskFileService = new TaskFileServiceImpl();
    }

    public void ui() {

        System.out.println("Welcome to Task-Manager application!");
        System.out.println("----------------------------------------");

        while (true) {
            System.out.println("1. Add 2. Edit 3. Delete 4. View 5. Exit");
            System.out.print("Please select one of the action menu to perform operations to task: ");
            int menuAction = scanner.nextInt();

            switch (menuAction) {
                case 1:
                    System.out.println("Please enter the item to add task or press the enter to return the menu.");
                    Task task = taskService.addTask();
                    System.out.println("Do you want to save this task in text file?");
                    System.out.print("Yes/No: ");
                    String choice = scanner.nextLine();

                    switch (choice) {
                        case "Yes":
                            taskFileService.addTasksToFile(task);
                            break;
                        case "No":
                            break;
                        default:
                            System.out.println("Invalid choice. Please select a valid option.");
                    }

                    break;
                case 2:
                    System.out.print("Please enter the item to edit task or press the enter to return the menu: ");
                    taskService.editTask();
                    break;
                case 3:
                    System.out.print("Please enter the id to delete task or press the enter to return the menu: ");
                    taskService.deleteTask();
                    break;
                case 4:
                case 5:
                    System.out.println("Exiting application.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}