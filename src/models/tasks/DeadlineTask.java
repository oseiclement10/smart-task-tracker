package models.tasks;

import console.Output;
import enums.PriorityLevel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DeadlineTask extends Task {

    private LocalDateTime dueDate;


    public DeadlineTask(String title, String description, PriorityLevel priority, LocalDateTime dueDate) {
        super(title, description, priority);
        this.dueDate = dueDate;
    }



    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public PriorityLevel getPriority() {
        if (this.priority == null) {
            long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), dueDate);
            return daysLeft > 10 ? PriorityLevel.LOW :
                    daysLeft > 5 ? PriorityLevel.MEDIUM :
                    PriorityLevel.HIGH;
        }
        return this.priority;
    }

    @Override
    public LocalDateTime getDueDate() {
        return dueDate;
    }





}
