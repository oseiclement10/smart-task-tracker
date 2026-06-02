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

    public void printTaskList(ArrayList<Task> tasks, String emptyMessage, String headerMessage) {
        if (tasks.isEmpty()) {
            this.printMessage(emptyMessage);
            this.printMessage("");
            return;
        }

        if (headerMessage != null && !headerMessage.isEmpty()) {
            this.printMessage(headerMessage);
        }

        this.printMessage("============ TASKS LIST ===============");
        this.printMessage(" ");
        this.printMessage(" ID  | TASK                      | PRIORITY | DEADLINE |  CREATED AT");
        this.printMessage("__________________________");
        for (Task task : tasks) {
            String deadline = task instanceof DeadlineTask ? ((DeadlineTask) task).getDueDateToString() : "n/a";
            this.printMessage(" " + (task.getId()) + "   | " + task + "    | " + task.getPriority() + "    | " + deadline + "   | " + task.getCreatedAt());
        }
        this.printMessage("__________________________");
        this.printMessage(" ");
    }
}
