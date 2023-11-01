package com.manager.model;

import lombok.Getter;

/**
 * @author Artur Tomeyan
 * @date 01.11.2023
 */
@Getter
public enum Status {

    ASSIGNED("Assigned"),
    CANCELED("Canceled"),
    CLOSED("Closed"),
    DECLINED("Declined"),
    DRAFT("Draft"),
    INPROCESS("In process"),
    PENDING("Pending"),
    PROJECTED("Projected"),
    REMOVED("Removed");

    private final String status;

    Status(String status) {
        this.status = status;
    }
}