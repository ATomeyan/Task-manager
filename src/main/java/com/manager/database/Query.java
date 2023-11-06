package com.manager.database;

public final class Query {

    private Query() {
    }

    public static final String createTable =
            "create table if not exists task (" +
                    "id varchar(8) not null primary key," +
                    "title varchar(255) not null," +
                    "description varchar(255) not null," +
                    "status varchar(255) not null," +
                    "due_date date not null," +
                    "task_created_at timestamp not null," +
                    "task_changed_at timestamp" +
            ");";

    public static final String viewAllTasks = "select * from task";

    public static final String insertIntoTable = "insert into task(id, title, description, due_date, status, task_created_at) values (?, ?, ?, ?, ?, ?)";

    public static final String delete = "delete from task where id = ?";

    public static final String findByID = "select * from task where id = ?";

    public static final String sortAllTasksByDueDate = "select * from task order by due_date";

    public static final String sortAllTasksByStatus = "select * from task order by status";

    public static final String updateTaskById = "update task set " +
            "title = ?, " +
            "description = ?, " +
            "status = ?, " +
            "due_date = ?" +
            "task_created_at = ?," +
            "task_changed_at = ?" +
            "where id = ?";
}