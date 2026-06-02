package models.tasks;

import enums.PriorityLevel;
import enums.TaskStatus;

import java.io.Serializable;
import java.time.LocalDate;

abstract public class Task implements Serializable {

    private static int nextId = 1;

    int id;
    String title;
    String description;
    private LocalDate createdAt;
    public TaskStatus status;
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
        return this.title ;
    }

    public String getCreatedAt(){
        return this.createdAt.toString();
    }


}
