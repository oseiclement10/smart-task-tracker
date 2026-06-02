package apps;

import console.AddTask;
import console.DeleteTask;
import console.Input;
import console.Output;
import enums.TaskStatus;
import models.tasks.SimpleTask;
import models.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TaskManager {
    ArrayList<Task> tasks;
    private final Input input;
    private final Output output;
    private final TaskRepository repository;

    public TaskManager(Input inputHandler, Output outputHandler) {

        this.tasks = new ArrayList<>();
        this.input = inputHandler;
        this.output = outputHandler;
        this.repository = new TaskRepository();
    }

    public void runCreateTask() {
        AddTask taskCreator = new AddTask(this.input, this.output);
        taskCreator.run(this::addTask);
    }

    public void printTasksList() {
        this.output.printTaskList(this.tasks);
    }

    public void runDeleteTasks() {
        DeleteTask taskDeleter = new DeleteTask(this.input, this.output);
        taskDeleter.run(this::bulkRemoveTasks, this.tasks);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public void bulkRemoveTasks(int[] taskIds) {
        Set<Integer> idsToDelete = Arrays.stream(taskIds).boxed().collect(Collectors.toSet());
        this.tasks.removeIf(t -> idsToDelete.contains(t.getId()));
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

    public void persist() {
        this.repository.save(this.tasks);
    }

    public void load() {
        ArrayList<Task> storedTasks = this.repository.load();
        this.tasks = storedTasks;
        Task.setNextId(storedTasks.isEmpty() ? 1 : storedTasks.size() + 1);
    }
}
