package com.manager.ui;

import com.manager.model.Task;
import com.manager.service.TaskFileService;
import com.manager.service.TaskFileServiceImpl;
import com.manager.service.TaskService;
import com.manager.service.TaskServiceImpl;
import com.manager.utils.Helper;

import java.util.List;
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
            System.out.println("1. Add 2. Edit 3. Delete 4. View 5. Update task file 6. Exit");
            System.out.print("Please select one of the action menu to perform operations to task: ");
            int menuAction = scanner.nextInt();

            switch (menuAction) {
                case 1 -> {
                    System.out.println("Please enter the item to add task or press the enter to return the menu.");
                    Task task = taskService.addTask();
                    addTaskInFile(task);
                }
                case 2 -> {
                    System.out.println("Please enter the task id to edit task or press the enter to return the menu: ");

                    taskService.editTask();
                    updateTasksInFile();
                }
                case 3 -> {
                    String id = Helper.getInput("Please enter the id to delete task or press the enter to return the menu: ");
                    deleteTask(id);
                }
                case 4 -> viewAllTasks();
                case 5 -> {
                    List<Task> tasks = taskService.viewAllTasks();
                    taskFileService.updateTaskFile(tasks);
                }
                case 6 -> {
                    System.out.println("Exiting application.");
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private void addTaskInFile(Task task) {
        System.out.println("Do you want to save this task in text file?");

        String choice = Helper.getInput("Yes/No: ");
        switch (choice) {
            case "Yes", "yes", "Y", "y" -> taskFileService.addTasksToFile(task);

            case "No", "no", "N", "n" -> {
            }
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    private void updateTasksInFile() {
        System.out.println("Do you want to update text file?");

        String choice = Helper.getInput("Yes/No: ");
        switch (choice) {
            case "Yes", "yes", "Y", "y" -> {
                List<Task> tasks = taskService.viewAllTasks();
                taskFileService.updateTaskFile(tasks);
            }
            case "No", "no", "N", "n" -> {
            }
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    private void viewAllTasks() {
        System.out.println("Which option do you want to see the tasks?");
        System.out.println("Choose one.");

        String choice = Helper.getInput("1 Sorted, 2 Filtered, 3 By ID, 4 View all -> ");

        switch (choice) {
            case "Sorted", "sorted", "SORTED", "1" -> viewSortedTasks();
            case "Filtered", "FILTERED", "filtered", "2" -> viewFilteredTasks();
            case "By ID", "BY ID", "by id", "3" -> {

                String id = Helper.getInput("Enter the task id: ");
                taskService.findById(id).ifPresent(System.out::println);
            }
            case "view all", "View all", "4" -> {
                List<Task> allTasks = taskService.viewAllTasks();
                for (Task task : allTasks) {
                    System.out.println(task);
                }
            }
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    private void viewSortedTasks() {
        System.out.println("There are two types of sorting by 1. DUE DATE 2. STATUS ");
        String choice = Helper.getInput("Choose one: ");

        switch (choice) {
            case "DUE DATE", "due date", "1" -> {
                List<Task> tasks = taskService.viewAllTasksSortedByDueDate();
                for (Task task : tasks) {
                    System.out.println(task);
                }
            }
            case "STATUS", "status", "2" -> {
                List<Task> tasks = taskService.viewAllTasksSortedByStatus();
                for (Task task : tasks)
                    System.out.println(task);
            }
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    private void viewFilteredTasks() {
        String status = Helper.getInput("Please enter status type: ");
        List<Task> tasks = taskService.tasksByFilteringStatus(status);

        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    private void deleteTask(String id) {
        System.out.println("Do you confirm ");
        String choice = Helper.getInput("Yes/No: ");

        switch (choice) {
            case "Yes", "yes", "Y", "y" -> {
                if (taskService.deleteTask(id)) {
                    System.out.println("Task deleted.");
                    updateTasksInFile();
                }
            }
            case "No", "no", "N", "n" -> {
            }
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }
}