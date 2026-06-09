package apps;

import models.tasks.Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TaskExport {
    public static String escape(String str) {
        return " \" " + str.replace("\"", "\"\"") + "\"";
    }

    public static String escape(int num) {
        return " \" " + String.valueOf(num).replace("\"", "\"\"") + "\"";
    }

    public void exportTasks(ArrayList<Task> tasks) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.csv"));
            bufferedWriter.write("ID,Title,Description,DueDate,Priority, Interval Days, Date Created");
            bufferedWriter.newLine();

            for (Task task : tasks) {
                bufferedWriter.write(task.toCsv());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        } catch (IOException ioException) {
            System.out.println("IO Exception occurred whiles exporting task ");
        }

    }
}
