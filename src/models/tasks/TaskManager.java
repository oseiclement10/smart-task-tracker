package models.tasks;

import models.tasks.enums.TaskStatus;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TaskManager<T extends Task> {
    ArrayList<T> tasks;

    public TaskManager() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(T task) {
        tasks.add(task);
    }

    public void removeTask(T task) {
        tasks.remove(task);
    }

    public ArrayList<T> getAll() {
        return this.tasks;
    }

    public ArrayList<T> filterByStatus(TaskStatus status) {
        return this.tasks.stream()
                .filter(task -> task.status == status)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<T> filterByTag(String tag) {
        return this.tasks.stream()
                .filter(task -> {
                    if (task instanceof SimpleTask) {
                        return ((SimpleTask) task).tags.contains(tag);
                    }
                    return false;
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<T> searchByRegex(String searchInput) {
        Pattern pattern = Pattern.compile(searchInput, Pattern.CASE_INSENSITIVE);
        return this.tasks.stream().filter(task -> pattern.matcher(task.getTitle()).find())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
