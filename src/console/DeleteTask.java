package console;

import enums.DeleteOption;
import models.tasks.DeadlineTask;
import models.tasks.RecurringTask;
import models.tasks.SimpleTask;
import models.tasks.Task;
import enums.PriorityLevel;
import enums.TaskType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class DeleteTask {
    private final Input input;
    private final Output output;

    public DeleteTask(Input inputHandler, Output outputHandler) {
        this.input = inputHandler;
        this.output = outputHandler;
    }

    public void run(Consumer<int[]> onTaskDeleted, ArrayList<Task> tasks) {
        while (true) {
            printOptions();
            int menuOptionSelected = this.input.getIntInput(
                    "Enter your choice : (1,2 or 0 to exit) ",
                    "[0-2]",
                    "input must be from 0 to 2"
            );
            if (menuOptionSelected == 0) {
                this.output.printMessage("Going back to main menu .... ");
                return;
            }

            boolean continueDeleting = this.handleUserSelection(tasks, menuOptionSelected, onTaskDeleted);
            if (!continueDeleting) {
                output.printMessage("exiting ...");
                return;
            }

        }


    }

    private int[] deleteTask(DeleteOption deleteOption, ArrayList<Task> tasks) {
        this.output.printTaskList(tasks);

        if (deleteOption == DeleteOption.SINGLE) {
            while (true) {
                int taskIndex = input.getIntInput(
                        "Enter id of task you wish to delete : ",
                        "^[1-9]+$",
                        "id must be a number"
                );

                Task taskToDelete = tasks.get(taskIndex - 1);

                if (taskToDelete != null) {
                    return new int[]{taskIndex};
                }

                output.printMessage("ID entered is not valid. Please enter a valid id ");

            }

        }

        if (deleteOption == DeleteOption.BULK) {
            while (true) {
                String taskIndexes = input.getStringInput(
                        "Task IDS",
                        "Enter id(s) of tasks you wish to delete separated by a comma eg, 12,13 : ",
                        "^([1-9]+,)+$",
                        "id must be a number"
                );

                int[] indexes = Arrays.stream(taskIndexes.split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();

                boolean allValid = Arrays.stream(indexes).allMatch(id -> id >= 1 && id <= tasks.size());

                if (allValid) {
                    return indexes;
                }

                output.printMessage("IDs entered are not valid . Please ensure that all ids are valid ");

            }

        }

        return new int[]{};
    }


    private boolean handleUserSelection(ArrayList<Task> tasks, int menuOption, Consumer<int[]> onTaskDeleted) {
        switch (menuOption) {
            case 1 -> onTaskDeleted.accept(deleteTask(DeleteOption.SINGLE, tasks));
            case 2 -> onTaskDeleted.accept(deleteTask(DeleteOption.BULK, tasks));
        }


        String continueCreating = input.getStringInput(
                "Option",
                "Would you like to delete more Tasks ? Reply with yes or no",
                "^(yes)|(no)$",
                "Answer must be yes or no"
        );

        return continueCreating.equals("yes");

    }


    private void printOptions() {
        this.output.printMessage("======== DELETE TASKS ======== ");
        this.output.printMessage("==== Select type of Action ===== ");

        Map<Integer, String> options = this.getAddOptions();

        options.forEach((Integer key, String value) -> {
            this.output.printMessage(key + ". " + value);
        });

        this.output.printMessage("==============================");

    }


    private Map<Integer, String> getAddOptions() {
        Map<Integer, String> menu = new LinkedHashMap<>();
        menu.put(1, "Simple Delete (Delete one at a time)");
        menu.put(2, "Bulk Delete");
        menu.put(0, "Exit");
        return menu;
    }
}
