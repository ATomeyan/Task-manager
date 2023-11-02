package com.manager.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Artur Tomeyan
 * @date 01.11.2023
 */
@Getter
@Setter
public class Task {
    private String id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private String status;
}