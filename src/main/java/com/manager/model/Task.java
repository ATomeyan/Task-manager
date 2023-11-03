package com.manager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Artur Tomeyan
 * @date 01.11.2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private String id;
    private String title;
    private String description;
    private String status;
    private LocalDate dueDate;
    private LocalDateTime taskCreatedAt;
    private LocalDateTime taskChangedAt;
}