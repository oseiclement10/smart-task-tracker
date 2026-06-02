package apps;

import models.tasks.Task;

import java.io.*;
import java.util.ArrayList;

public class TaskRepository {
    public void save(ArrayList<Task> tasks) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("storage.dat"));
            objectOutputStream.writeObject(tasks);
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("could not save tasks, storage file not found");
        } catch (IOException ioException) {
            System.out.println("IO Exception occurred " + ioException.getMessage());
        }

    }

    @SuppressWarnings("unchecked")
    public ArrayList<Task> load() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("storage.dat"));
            Object obj = objectInputStream.readObject();
            if (obj instanceof ArrayList<?>) {
                return (ArrayList<Task>) obj;
            }

            return new ArrayList<>();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("could not load data, storage file missing");
            return new ArrayList<>();
        } catch (IOException ioException) {
            System.out.println("IO Exception occurred " + ioException.getMessage());
            return new ArrayList<>();
        } catch (ClassNotFoundException classNotFoundException) {
            System.out.println("Class not found exception occurred");
            return new ArrayList<>();
        }

    }
}
