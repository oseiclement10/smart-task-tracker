package apps;

import console.Output;
import models.tasks.DeadlineTask;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reminder {
    private final TaskManager taskManager;
    private final Output output;

    public Reminder(TaskManager taskHandler, Output outputHandler) {
        this.taskManager = taskHandler;
        this.output = outputHandler;
    }

    public void activate() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                LocalDateTime now = LocalDateTime.now();
                this.taskManager.getAll().forEach(task -> {
                    if (task instanceof DeadlineTask) {
                        LocalDateTime dueDate = ((DeadlineTask) task).getDueDate();
                        if ((!dueDate.isBefore(now)) && (!dueDate.isAfter(now.plusHours(24)))) {
                            this.output.printMessage("======= Due Task ===========");
                            this.output.printMessage(task.toString());
                        }
                    }
                });
                Thread.sleep(60000);

            }
        } catch (InterruptedException exception) {
            System.out.println("Thread interrupted");
        }

    }
}
