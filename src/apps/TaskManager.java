package apps;

import console.*;
import enums.PriorityLevel;
import enums.SortDirection;
import enums.TaskSortType;
import enums.TaskStatus;
import models.tasks.SimpleTask;
import models.tasks.Task;

import java.util.*;
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

    public void runViewTasks() {
        ViewTask viewTaskRunner = new ViewTask(this.input, this.output, this);
        viewTaskRunner.run();
    }

    public void runDeleteTasks() {
        DeleteTask taskDeleter = new DeleteTask(this.input, this.output);
        taskDeleter.run(this::bulkRemoveTasks, this.tasks);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }


    public void bulkRemoveTasks(int[] taskIds) {
        Set<Integer> idsToDelete = Arrays.stream(taskIds).boxed().collect(Collectors.toSet());
        this.tasks.removeIf(t -> idsToDelete.contains(t.getId()));
    }

    public synchronized ArrayList<Task> getAll() {
        return new ArrayList<>(this.tasks);
    }

    public ArrayList<Task> filterByStatus(TaskStatus status) {
        return this.tasks.stream()
                .filter(task -> task.status == status)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Task> filterByPriority(PriorityLevel level) {
        return this.tasks.stream()
                .filter(task -> task.getPriority() == level)
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

    public Optional<Task> findByTaskId(int id) {
        return this.tasks.stream().filter(t -> t.getId() == id).findFirst();
    }

    public void sort(TaskSortType type, SortDirection direction) {
        boolean isDescending = direction == SortDirection.DESCENDING;
        switch (type) {
            case TaskSortType.DUEDATE -> {
                Comparator<Task> comparator = Comparator.comparing(
                        Task::getDueDate,
                        Comparator.nullsLast(Comparator.naturalOrder())
                );
                if (isDescending) {
                    comparator = comparator.reversed();
                }
                this.tasks.sort(comparator);

            }

            case TaskSortType.PRIORITY -> {
                Comparator<Task> comparator = Comparator.comparing((Task::getPriority));
                if (isDescending) {
                    comparator = comparator.reversed();
                }
                this.tasks.sort(comparator);
            }

            case TaskSortType.CREATEDAT -> {
                Comparator<Task> comparator = Comparator.comparing(Task::getCreatedAt);
                if (isDescending) {
                    comparator = comparator.reversed();
                }
                this.tasks.sort(comparator);

            }
        }

    }

    public void persist() {
        this.repository.save(this.tasks);
    }

    public void load() {
        ArrayList<Task> storedTasks = this.repository.load();
        if (!storedTasks.isEmpty()) {
            this.tasks = storedTasks;
            int nextId = storedTasks.stream().mapToInt(Task::getId).max().orElse(1);
            Task.setNextId(nextId + 1);
        }
    }


}
