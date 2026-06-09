import console.ConsoleUI;

void main() {
    IO.println("Welcome to Smart Task tracker !");
    ConsoleUI console = new ConsoleUI();
    Thread reminderThread = new Thread(console::startReminder);
    reminderThread.start();
    console.main(reminderThread);
}
