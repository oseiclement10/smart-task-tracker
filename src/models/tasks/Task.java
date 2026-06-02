package models.tasks;

import enums.PriorityLevel;
import enums.TaskStatus;

import java.time.LocalDate;

abstract public class Task {

    private static int nextId = 1;

    int id;
    String title;
    String description;
    LocalDate createdAt;
    TaskStatus status;
    PriorityLevel priority;

    Task(String title, String description, PriorityLevel priority) {
        this.id = nextId;
        this.title = title;
        this.description = description;
        this.createdAt = LocalDate.now();
        this.status = TaskStatus.TODO;
        this.priority = priority;
        nextId++;
    }

    public static void setNextId(int nextId) {
        Task.nextId = nextId;
    }

    abstract public PriorityLevel getPriority();

    public int getId() {
        return id;
    }

    public String getTitle() {
        return this.title;
    }

    public String toString() {
        return this.title + " priority of " + this.priority.toString();
    }
}
