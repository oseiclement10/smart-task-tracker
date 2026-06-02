package console;

import models.tasks.DeadlineTask;
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
            this.printMessage("");
            return;
        }
        this.printMessage("============TASKS LIST ===============");
        this.printMessage(" ");
        this.printMessage(" ID  | TASK | PRIORITY | DEADLINE |  ");
        this.printMessage("__________________________");
        for (Task task : tasks) {
            this.printMessage(" " + (task.getId()) + "   | " + task + "    | " + task.getPriority() ) ;
        }
        this.printMessage("__________________________");
        this.printMessage(" ");
    }
}
