package models.tasks;

import models.tasks.enums.PriorityLevel;

import java.time.LocalDate;

public class RecurringTask extends  DeadlineTask{
    int recurrenceIntervalDays;

    public RecurringTask(String title, String description, PriorityLevel priority, String dueDate, boolean isRecurring, int recurInterval){
        super(title,description,priority,dueDate,isRecurring);
        this.recurrenceIntervalDays = recurInterval;
     }

     public void advanceToNextOccurrence(){
        this.dueDate = this.dueDate.plusDays(this.recurrenceIntervalDays);
     }
}
