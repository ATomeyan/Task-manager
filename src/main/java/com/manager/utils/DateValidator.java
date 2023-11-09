package com.manager.utils;

import java.time.LocalDate;

public final class DateValidator {

    private DateValidator() {
    }

    public static boolean validateDate(LocalDate date) {
        if (LocalDate.now().isBefore(date))
            return true;
        else {
            System.out.println("The due date is not valid and the task was rejected. Please input a valid due date.");
            return false;
        }
    }
}
