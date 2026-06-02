package console;

import apps.TaskManager;
import enums.TaskType;
import models.tasks.Task;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ViewTask {
    private final Input input;
    private final Output output;
    private final TaskManager taskManager;

    public ViewTask(Input inputHandler, Output outputHandler, TaskManager taskManager) {
        this.input = inputHandler;
        this.output = outputHandler;
        this.taskManager = taskManager;
    }

    public void run() {
        while (true) {
            this.output.printTaskList(this.taskManager.getAll(),"No tasks found" , null);

            printOptions();
            int menuOptionSelected = this.input.getIntInput(
                    "Which action would you want to perform? Enter your choice : (1,2,3) ",
                    "[0-3]",
                    "input must be from 0 to 3"
            );
            if (menuOptionSelected == 0) {
                this.output.printMessage("Going back to main menu .... ");
                return;
            }

            boolean continueCreating = this.handleUserSelection(menuOptionSelected);
            if (!continueCreating) {
                output.printMessage("exiting ...");
                return;
            }

        }


    }

    private boolean handleUserSelection(int menuOption) {
        switch (menuOption) {
            case 1 -> this.onSearchSelect();
//            case 2 -> onTaskCreated.accept(createTask(TaskType.DEADLINE));
//            case 3 -> onTaskCreated.accept(createTask(TaskType.RECURRING));
        }


        String continueCreating = input.getStringInput(
                "Option",
                "Would you like to to go back to main menu",
                "^(yes)|(no)$",
                "Answer must be yes or no"
        );

        return continueCreating.equals("yes");

    }


    private void printOptions() {
        this.output.printMessage("============TASKS LIST ===============");
        this.output.printMessage(" ");
        Map<Integer, String> options = this.getViewActions();

        options.forEach((Integer key, String value) -> {
            this.output.printMessage(key + ". " + value);
        });

        this.output.printMessage("==============================");

    }


    private Map<Integer, String> getViewActions() {
        Map<Integer, String> menu = new LinkedHashMap<>();
        menu.put(1, "Search Tasks");
        menu.put(2, "Sort Tasks");
        menu.put(3, "Filter Tasks");
        menu.put(0, "Exit");
        return menu;
    }

    private void onSearchSelect() {
        try {

            while (true) {
                String keyword = this.input.getStringInput("search",
                        "Enter search value here ",
                        "[A-Za-z0-9 ]+",
                        "must be a valid word"
                );
                this.output.printMessage("searching .... ");
                ArrayList<Task> tasks = this.taskManager.getAll().stream()
                        .filter(task -> task.getTitle().contains(keyword))
                        .collect(Collectors.toCollection(ArrayList::new));

                Thread.sleep(600);
                this.output.printMessage("");


                this.output.printTaskList(tasks, "No results matching your search", +tasks.size() + " Results Found");

                String continueSearching = input.getStringInput(
                        "Option",
                        "Would you like to continue searching (yes or no)",
                        "^(yes)|(no)$",
                        "Answer must be yes or no"
                );

                if (continueSearching.equals("no")) return;

            }


        } catch (InterruptedException interruptedException) {
            this.output.printMessage("Thread was interrupted !");
        }


    }

}
