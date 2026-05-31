package console;

import models.tasks.Task;
import models.tasks.TaskManager;

import java.util.LinkedHashMap;
import java.util.Map;


public class ConsoleUI {
    public Map<Integer, String> menu;
    private final Input input;
    private final Output output;
    private final TaskManager taskManager;

    public ConsoleUI() {
        this.output = new Output();
        this.input = new Input(this.output);
        this.loadMainMenu();
        this.taskManager = new TaskManager();
    }

    public void main() {
        while (true) {
            printMainMenu();
            int menuOptionSelected = this.input.getIntInput("Enter your choice : ",
                    "[0-4]",
                    "from 0 to 4"
            );

            if (menuOptionSelected == 0) {
                this.output.printMessage("Exiting application .... ");
                return;
            }
            this.handleUserSelection(menuOptionSelected);
        }

    }

    private void printMainMenu() {
        this.output.printMessage("======== Welcome To Smart Task Tracker ======== ");
        this.menu.forEach((Integer key, String value) -> {
            this.output.printMessage(key + ". " + value);
        });
        this.output.printMessage("==============================================");
    }


    private void handleUserSelection(int menuOption) {
        switch (menuOption) {
            case 1:
                this.taskManager.runCreateTask(this.input, this.output);
                break;
            case 2:
                this.output.printTaskList(this.taskManager.getAll());
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }



    private void loadMainMenu() {
        menu = new LinkedHashMap<>();
        menu.put(1, "Add Task");
        menu.put(2, "View Tasks");
        menu.put(3, "Delete Tasks");
        menu.put(4, "Export Tasks");
        menu.put(0, "Exit");
    }
}
