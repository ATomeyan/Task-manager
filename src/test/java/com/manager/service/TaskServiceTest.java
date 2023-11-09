package com.manager.service;

import com.manager.database.connector.DBConnector;
import com.manager.model.Task;
import com.manager.utils.Helper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    private static TaskService taskService;
    private static final DBConnector dbConnector = DBConnector.getDbConnector();

    @BeforeAll
    static void init() {

        taskService = new TaskServiceImpl();

        try {
            dbConnector.connection().setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void teardown() {
        try {
            dbConnector.connection().setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addUserTest() {
        Task task = new Task();

        task.setId(Helper.generateId());
        task.setTitle("Feature");
        task.setDescription("Create button.");
        task.setStatus("Pending");
        task.setDueDate(LocalDate.of(2023, 11, 10));
        task.setTaskCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        taskService.addTask(task);

        Optional<Task> taskFromDB = taskService.findById(task.getId());
        assertTrue(taskFromDB.isPresent(), task.getId());
    }

    @Test
    void findByIdTest() {
        Optional<Task> taskFromDB = taskService.findById("nLLV9Sy8");
        assertTrue(taskFromDB.isPresent(), "Object found");
    }

    @Test
    void deleteTaskTest() {

        taskService.deleteTask("uSSvepNW");

        Optional<Task> taskFromDB = taskService.findById("uSSvepNW");
        assertFalse(taskFromDB.isPresent(), "The Object was deleted");
    }
}