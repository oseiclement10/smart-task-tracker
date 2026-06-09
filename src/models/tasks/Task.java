package models.tasks;

import apps.TaskExport;
import console.Output;
import enums.PriorityLevel;
import enums.TaskStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

abstract public class Task implements Serializable {

    private static int nextId = 1;
    private static final long serialVersionUID = 1L;

    int id;
    private String title;
    private String description;
    private final LocalDateTime createdAt;
    public TaskStatus status;
    PriorityLevel priority;

    Task(String title, String description, PriorityLevel priority) {
        this.id = nextId;
        this.title = title;
        this.description = description;
        this.createdAt = LocalDateTime.now();
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPriority(PriorityLevel priorityLevel) {
        this.priority = priorityLevel;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public String toString() {
        return this.title;
    }

    public String toCsv() {
        String recurringInterval = this instanceof RecurringTask ? String.valueOf(((RecurringTask) this).getRecurrenceIntervalDays()) : "n/a";

        return this.id + "," +
                TaskExport.escape(this.title) + "," +
                TaskExport.escape(this.description) + "," +
                Output.formatDateTime(this.getDueDate()) + "," +
                this.priority.toString() + "," +
                recurringInterval + "," +
                 Output.formatDateTime(this.getDueDate());
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getDueDate() {
        return null;
    }


}
