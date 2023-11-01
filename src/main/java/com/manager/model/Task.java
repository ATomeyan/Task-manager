package com.manager.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Artur Tomeyan
 * @date 01.11.2023
 */
@Getter
@Setter
public class Task {

    private String title;
    private String description;
    private LocalDate dueDate;
    private Status status;
}