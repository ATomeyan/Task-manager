package com.manager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;

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
    private Timestamp taskCreatedAt;
    private Timestamp taskChangedAt;

    @Override
    public String toString() {
        return "\nid: '" + id + '\'' +
                "\ntitle: '" + title + '\'' +
                "\ndescription: '" + description + '\'' +
                "\nstatus: '" + status + '\'' +
                "\ndueDate: " + dueDate +
                "\ntaskCreatedAt: " + taskCreatedAt +
                "\ntaskChangedAt: " + taskChangedAt;
    }
}