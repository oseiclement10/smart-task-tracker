package models.tasks;

import enums.PriorityLevel;

import java.time.LocalDateTime;

public class RecurringTask extends  DeadlineTask{
    int recurrenceIntervalDays;

    public RecurringTask(String title, String description, PriorityLevel priority, LocalDateTime dueDate, int recurInterval){
        super(title,description,priority,dueDate);
        this.recurrenceIntervalDays = recurInterval;
     }

     public void setRecurrenceIntervalDays(int recurrenceIntervalDays){
        this.recurrenceIntervalDays = recurrenceIntervalDays;
     }

     public int getRecurrenceIntervalDays(){
        return this.recurrenceIntervalDays;
     }

     public void advanceToNextOccurrence(){
        this.setDueDate(this.getDueDate().plusDays(this.recurrenceIntervalDays));
     }
}
