package com.manager.database;

public final class Query {

    private Query() {
    }

    public static final String createTable =
            "create table if not exists task (" +
                    "id varchar(36) not null," +
                    "title varchar(255) not null," +
                    "description varchar(255) not null," +
                    "status varchar(255) not null," +
                    "due_date date not null," +
                    "task_created_at datetime not null," +
                    "task_changed_at datetime" +
                    ");";

    public static final String viewAllTasks = "select * from task";

    public static final String insertIntoTable = "insert into task(id, title, description, due_date, status, created_at) values (?, ?, ?, ?, ?, ?)";

    public static final String delete = "delete from task where id = ?";

    public static final String findByID = "select * from task where id = ?";

    public static final String sortAllTasksByDueDate = "select * from task order by due_date";

    public static final String sortAllTasksByStatus = "select * from task order by status";
}