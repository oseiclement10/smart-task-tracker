package console;

import models.tasks.DeadlineTask;
import models.tasks.Task;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Output {
    private final PrintStream out;
    private  static final String spacing = "%-5s | %-30s | %-45s | %-15s | %-20s | %-20s%n";

    public Output() {
        out = System.out;
    }

    public void printMessage(String message) {
        out.println(message);
    }

    public void printTaskList(ArrayList<Task> tasks, String emptyMessage, String headerMessage) {
        if (tasks.isEmpty()) {
            this.printMessage(emptyMessage);
            this.printMessage("");
            return;
        }

        if (headerMessage != null && !headerMessage.isEmpty()) {
            this.printMessage(headerMessage);
        }



        this.printMessage("=".repeat(60) + " TASKS LIST " + "=".repeat(60));
        this.printMessage(" ");

        System.out.printf(spacing, "ID", "Title", "DESCRIPTION", "PRIORITY", "DEADLINE", "CREATED AT");

        this.printMessage("-".repeat(150));
        for (Task task : tasks) {
            String deadline = formatDateTime(task.getDueDate());
            String createdAt = formatDateTime(task.getCreatedAt());
            System.out.printf(spacing,
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getPriority(),
                    deadline,
                    createdAt
            );
        }
        this.printMessage("-".repeat(150));
    }

    public void printTask(Task task) {
        this.out.println("TASK DETAILS");
        this.printMessage("-".repeat(150));
        String deadline = formatDateTime(task.getDueDate());
        String createdAt = formatDateTime(task.getCreatedAt());
        System.out.printf(spacing,
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                deadline,
                createdAt
        );
        this.printMessage("-".repeat(150));
    }


    public static String formatDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "n/a";
        }
        return getDateOrdinal(localDateTime.getDayOfMonth()) + " " + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    private static String getDateOrdinal(int day) {
        if (day >= 11 && day <= 13) return day + "th";
        return switch (day % 10) {
            case 1 -> day + "st";
            case 2 -> day + "nd";
            case 3 -> day + "rd";
            default -> day + "th";
        };
    }

}
