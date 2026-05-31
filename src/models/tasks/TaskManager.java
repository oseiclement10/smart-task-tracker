package models.tasks;

import console.AddTask;
import console.Input;
import console.Output;
import models.tasks.enums.TaskStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TaskManager {
    ArrayList<Task> tasks;

    public TaskManager() {
        this.tasks = new ArrayList<>();
    }

    public void runCreateTask(Input input, Output output){
        AddTask taskCreator = new AddTask(input,output);
        taskCreator.run(this::addTask);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public ArrayList<Task> getAll() {
        return this.tasks;
    }

    public ArrayList<Task> filterByStatus(TaskStatus status) {
        return this.tasks.stream()
                .filter(task -> task.status == status)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Task> filterByTag(String tag) {
        return this.tasks.stream()
                .filter(task -> {
                    if (task instanceof SimpleTask) {
                        return ((SimpleTask) task).tags.contains(tag);
                    }
                    return false;
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Task> searchByRegex(String searchInput) {
        Pattern pattern = Pattern.compile(searchInput, Pattern.CASE_INSENSITIVE);
        return this.tasks.stream().filter(task -> pattern.matcher(task.getTitle()).find())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void sort(Comparator<Task> comparator) {
        this.tasks.sort(comparator);
    }
}
