package console;

import models.tasks.Task;

import java.io.PrintStream;
import java.util.ArrayList;

public class Output {
    private final PrintStream out;

    public Output() {
        out = System.out;
    }

    public void printMessage(String message) {
        out.println(message);
    }

    public void printTaskList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            this.printMessage("No tasks available");
            return;
        }
        this.printMessage("============TASKS LIST ===============");
        for (int i = 0; i < tasks.size(); i++) {
            this.printMessage((i + 1) + " . " + tasks.get(i));
        }
        this.printMessage("======================================");
    }
}
