package console;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;


public class Input {
    private final Scanner scanner;
    private final Output output;

    public Input(Output output) {
        scanner = new Scanner(System.in);
        this.output = output;
    }

    public int getIntInput(String label, String validatingRegex, String errorMessage) {

        while (true) {
            output.printMessage(label);
            String input = this.scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                Optional<String> validationError = InputValidator.validateByPattern(
                        input,
                        validatingRegex,
                        errorMessage
                );
                if (validationError.isEmpty()) {
                    return value;
                }
                output.printMessage(validationError.get());
            } catch (NumberFormatException e) {
                output.printMessage("Please type in a number");
            }

        }

    }


    public String getStringInput(String fieldName, String label, String validatingRegex, String errorMessage) {

        while (true) {
            output.printMessage(label);
            String input = this.scanner.nextLine().trim();

            Optional<String> validationError = InputValidator.validateNotEmpty(input, fieldName);

            if (validationError.isEmpty()) {
                validationError = InputValidator.validateByPattern(input, validatingRegex, errorMessage);
                if (validationError.isEmpty()) {
                    return input;
                }
            }

            output.printMessage(validationError.get());

        }

    }

    public LocalDateTime getDateTimeInput(String fieldName, String label) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        while (true) {
            String dueDate = this.getStringInput(
                    fieldName,
                    label,
                    "[0-9]{4}-[0-9]{2}-[0-9]{2} ([01][0-9]|2[0-3]):[0-5][0-9]",
                    "Date should be in the format yyyy-MM-dd HH:mm"
            );

            try {
                return LocalDateTime.parse(dueDate, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Date should be in the format yyyy-MM-dd HH:mm " );
            }

        }


    }


}
