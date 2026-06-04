package console;

import models.tasks.DeadlineTask;
import models.tasks.RecurringTask;
import models.tasks.SimpleTask;
import models.tasks.Task;
import enums.PriorityLevel;
import enums.TaskType;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class AddTask {
    private final Input input;
    private final Output output;

    public AddTask(Input inputHandler, Output outputHandler) {
        this.input = inputHandler;
        this.output = outputHandler;
    }

    public void run(Consumer<Task> onTaskCreated) {
        while (true) {
            printOptions();
            int menuOptionSelected = this.input.getIntInput(
                    "Enter your choice : (1,2,3) ",
                    "[0-3]",
                    "input must be from 0 to 3"
            );
            if (menuOptionSelected == 0) {
                this.output.printMessage("Going back to main menu .... ");
                return;
            }

            boolean continueCreating = this.handleUserSelection(menuOptionSelected, onTaskCreated);
            if (!continueCreating) {
                output.printMessage("exiting ...");
                return;
            }

        }


    }

    private Task createTask(TaskType taskType) {
        LocalDateTime dueDate = null;
        int recurringDays = 0;

        String title = input.getStringInput(
                "Title",
                "Enter title of task : ",
                "[A-Za-z0-9 ]{4,}$",
                "Title must contain only letters and numbers and be at least 4 characters long."
        );

        String description = input.getStringInput(
                "Description",
                "Enter description of tasks : ",
                "[A-Za-z0-9 ]{4,}$",
                "Description must contain only letters and numbers and be at least 4 characters long."
        );

        String priorityStr = input.getStringInput(
                "Priority (high or low or medium)",
                "Enter priority (high or low or medium) ",
                "^(high)|(low)|(medium)$",
                "Priority Must be either high, low or medium "
        );

        PriorityLevel priority = PriorityLevel.valueOf(priorityStr.toUpperCase());

        if (taskType == TaskType.DEADLINE || taskType == TaskType.RECURRING) {
            dueDate = input.getDateTimeInput(
                    "Due date",
                    "Enter due date here. (Date should be in the format yyyy-mm-dd hh:mm)"
            );
        }

        if (taskType == TaskType.RECURRING) {

            recurringDays = input.getIntInput(
                    "Enter number of days it takes to reoccur",
                    "^[1-9]+",
                    "Must be a number"
            );

        }

        return switch (taskType) {
            case TaskType.SIMPLE -> new SimpleTask(title, description, priority);
            case TaskType.DEADLINE -> new DeadlineTask(title, description, priority, dueDate);
            case TaskType.RECURRING -> new RecurringTask(title, description, priority, dueDate, recurringDays);
        };


    }


    private boolean handleUserSelection(int menuOption, Consumer<Task> onTaskCreated) {
        switch (menuOption) {
            case 1 -> onTaskCreated.accept(createTask(TaskType.SIMPLE));
            case 2 -> onTaskCreated.accept(createTask(TaskType.DEADLINE));
            case 3 -> onTaskCreated.accept(createTask(TaskType.RECURRING));
        }

        output.printMessage("Task Created Successfully !!! ");

        String continueCreating = input.getStringInput(
                "Option",
                "Would you like to create more Tasks ? Reply with yes or no",
                "^(yes)|(no)$",
                "Answer must be yes or no"
        );

        return continueCreating.equals("yes");

    }


    private void printOptions() {
        this.output.printMessage("======== Add New Task ======== ");
        this.output.printMessage("==== Select type of Task ===== ");
        Map<Integer, String> options = this.getAddOptions();

        options.forEach((Integer key, String value) -> {
            this.output.printMessage(key + ". " + value);
        });

        this.output.printMessage("==============================");

    }


    private Map<Integer, String> getAddOptions() {
        Map<Integer, String> menu = new LinkedHashMap<>();
        menu.put(1, "Simple Task");
        menu.put(2, "Deadline Task");
        menu.put(3, "Recurring Task");
        menu.put(0, "Exit");
        return menu;
    }
}
