import console.ConsoleUI;

void main() {
    IO.println("Welcome to Smart Task tracker !");
    ConsoleUI console = new ConsoleUI();
    Thread remainderThread = new Thread(console::startReminder);
    remainderThread.start();
    console.main(remainderThread);
}
