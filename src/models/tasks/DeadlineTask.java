package models.tasks;

import models.tasks.enums.PriorityLevel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DeadlineTask extends Task {

    LocalDate dueDate;
    boolean recurring;

    public DeadlineTask(String title, String description, PriorityLevel priority, String dueDate, boolean isRecurring) {
        super(title, description, priority);
        this.dueDate = this.stringToDate(dueDate);
        this.recurring = isRecurring;
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
    public String toString(){
        String defaultStr = super.toString();
        return defaultStr + " due at " + this.dueDate;
    }

    private LocalDate stringToDate(String dateInput) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateInput, formatter);
    }
}
