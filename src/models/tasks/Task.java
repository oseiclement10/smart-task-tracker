package models.tasks;

import models.tasks.enums.PriorityLevel;
import models.tasks.enums.TaskStatus;

import java.time.LocalDate;
import java.util.UUID;

abstract public class Task {
    UUID id;
    String title;
    String description;
    LocalDate createdAt;
    TaskStatus status;
    PriorityLevel priority;




    Task(String title, String description, PriorityLevel priority) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.createdAt = LocalDate.now();
        this.status = TaskStatus.TODO;
        this.priority = priority;
    }


    abstract public PriorityLevel getPriority();

    public String getTitle(){
        return this.title;
    }

    public String toString(){
        return this.title + " priority of " + this.priority.toString();
    }
}
