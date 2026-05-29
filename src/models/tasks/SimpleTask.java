package models.tasks;

import models.tasks.enums.PriorityLevel;

import java.util.List;

public class SimpleTask extends Task {
    List<String> tags;

    public SimpleTask(String title, String description, PriorityLevel priority) {
        super(title, description, priority);
    }

    @Override
    public PriorityLevel getPriority() {
        return this.priority;
    }
}
