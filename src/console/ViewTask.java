package console;

import apps.TaskManager;
import enums.SortDirection;
import enums.TaskSortType;
import enums.TaskType;
import models.tasks.Task;

import javax.swing.*;
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

            this.output.printTaskList(this.taskManager.getAll(), "No tasks found", null);

            printOptions("ACTIONS", this.getViewActions());

            int menuOptionSelected = this.input.getIntInput(
                    "Which action would you want to perform? Enter your choice : (1,2,3) or 0 to exit ",
                    "[0-3]",
                    "input must be from 0 to 3"
            );
            if (menuOptionSelected == 0) {
                this.output.printMessage("Going back to main menu .... ");
                return;
            }

            switch (menuOptionSelected) {
                case 1 -> this.onSearchSelect();
                case 2 -> this.onSortSelect();
                //            case 3 -> onTaskCreated.accept(createTask(TaskType.RECURRING));
            }


        }


    }


    private void printOptions(String title, Map<Integer, String> options) {
        this.output.printMessage("============ " + title + "===============");

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
                        .filter(task -> task.getTitle().toLowerCase().contains(keyword.toLowerCase()))
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

    private void onSortSelect() {
        try {
            while (true) {
                this.printOptions("Sort Options ", this.getSortOptions());
                int sortType = this.input.getIntInput("what would you like to sort by (1-3) ", "^[0-3]$", "Must be a number,either 1 or 2 or 3");
                this.printOptions("Sort Direction", this.getSortDirections());
                int sortSortDirection = this.input.getIntInput("choose sort direction", "^[0-2]$", "Must be a number,either 1 or 2");

                TaskSortType type = sortType == 1 ? TaskSortType.DUEDATE : sortType == 2 ? TaskSortType.PRIORITY : TaskSortType.CREATEDAT;
                SortDirection direction = sortSortDirection == 1 ? SortDirection.ASCENDING : SortDirection.DESCENDING;

                this.taskManager.sort(type, direction);
                this.output.printMessage("sorting ... ");
                Thread.sleep(600);

                this.output.printTaskList(this.taskManager.getAll(), "no tasks found ", "Sorting Results");

                String continueSearching = input.getStringInput(
                        "Option",
                        "Would you like to continue sorting (yes or no)",
                        "^(yes)|(no)$",
                        "Answer must be yes or no"
                );

                if (continueSearching.equals("no")) return;


            }
        } catch (InterruptedException exception) {
            this.output.printMessage("Interrupt exception occured during sorting");
        }


    }

    private Map<Integer, String> getSortOptions() {
        Map<Integer, String> sortFieldsMenu = new LinkedHashMap<>();
        sortFieldsMenu.put(1, "Due Date");
        sortFieldsMenu.put(2, "Priority");
        sortFieldsMenu.put(3, "Date Created");
        sortFieldsMenu.put(0, "Exit");
        return sortFieldsMenu;
    }

    private Map<Integer, String> getSortDirections() {
        Map<Integer, String> sortFieldsMenu = new LinkedHashMap<>();
        sortFieldsMenu.put(1, "ASC (ascending)");
        sortFieldsMenu.put(2, "DSC (descending)");
        sortFieldsMenu.put(0, "Go Back");
        return sortFieldsMenu;
    }

}
