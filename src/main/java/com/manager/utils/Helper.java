package com.manager.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Scanner;

public class Helper {

    private Helper() {
    }

    public static String getInput(String prompt) {
        System.out.print(prompt);

        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static String generateId() {
        return RandomStringUtils.randomAlphanumeric(8);
    }
}
