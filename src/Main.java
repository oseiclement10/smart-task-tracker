import models.tasks.*;
import models.tasks.enums.PriorityLevel;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    IO.println("Welcome to Smart Task tracker !");
    IO.println("-----------------------------------------");
    SimpleTask simpleTask = new SimpleTask(
            "Practice Java",
            "Practice Java by building a real world projet",
            PriorityLevel.MEDIUM
    );

    DeadlineTask deadlineTask = new DeadlineTask(
            "Finish up smart task tracker",
            "Finish up task tracker, by setting csv and other exports",
            PriorityLevel.MEDIUM,
            "2026-06-30",
            true
    );

    RecurringTask recurringTask = new RecurringTask(
            "Take interview prep",
            "Take weekly interview assessment progress",
            PriorityLevel.MEDIUM,
            "2026-07-30",
            true,
            7
    );

    TaskManager<Task> taskManager = new TaskManager<>();
    taskManager.addTask(simpleTask);
    taskManager.addTask(deadlineTask);
    taskManager.addTask(recurringTask);

    System.out.println(taskManager.getAll());

}
