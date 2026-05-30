package console;

import java.util.LinkedHashMap;
import java.util.Map;

public class AddTask {
    private final Input input;
    private final Output output;

    public AddTask(Input inputHandler, Output outputHandler) {
        this.input = inputHandler;
        this.output = outputHandler;
    }

    public void run() {
        while (true) {
            printOptions();
            int menuOptionSelected = readEnteredMenuOption();
            if (menuOptionSelected == 0) {
                this.output.printMessage("Going back to main menu .... ");
                return;
            }
            this.handleUserSelection(menuOptionSelected);
        }
    }

    private void getTaskInfo(){
        String name = input.getStringInput(
                "Title",
                "(?=.*[A-Za-z])[A-Za-z0-9]{4,}$",
                "Must be a valid title, ie contain at least 4 characters and not all numbers"
        );

        String description = input.getStringInput(
                "Description",
                "[A-Za-z0-9]{4,}$",
                "Must be a valid word ie contain at least 4 characters "
        );


    }

    private int readEnteredMenuOption() {
        return this.input.getIntInput("[0-3]", "from 0 to 3");
    }

    private void handleUserSelection(int menuOption) {
        switch (menuOption) {
            case 1:
                break;
            case 2:

                break;
            case 3:
                break;

        }
    }


    private void printOptions() {
        this.output.printMessage("======== Add New Task ======== ");
        this.output.printMessage("==== Select type of Task ===== ");
        Map<Integer, String> options = this.getAddOptions();

        options.forEach((Integer key, String value) -> {
            this.output.printMessage(key + ". " + value);
        });

        this.output.printMessage("==============================");
        this.output.printMessage("Enter your choice : ");
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
