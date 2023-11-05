package com.manager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("title", title)
                .append("description", description)
                .append("status", status)
                .append("dueDate", dueDate)
                .append("taskCreatedAt", taskCreatedAt)
                .append("taskChangedAt", taskChangedAt)
                .toString();
    }
}