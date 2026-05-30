package console;

import java.util.Optional;
import java.util.Scanner;


public class Input {
    private final Scanner scanner;
    private final Output output;

    public Input(Output output) {
        scanner = new Scanner(System.in);
        this.output = output;
    }

    public int getIntInput(String validatingRegex, String patternDesc) {

        while (true) {
            String input = this.scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                Optional<String> validationError = InputValidator.validateByPattern(
                        input,
                        validatingRegex,
                        patternDesc
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


    public String getStringInput(String fieldName, String validatingRegex, String patternDesc) {

        while (true) {
            String input = this.scanner.nextLine().trim();

            Optional<String> validationError = InputValidator.validateNotEmpty(input, fieldName);

            if (validationError.isEmpty()) {
                validationError = InputValidator.validateByPattern(input, validatingRegex, patternDesc);
                if (validationError.isEmpty()) {
                    return input;
                }
            }

            output.printMessage(validationError.get());

        }

    }


}
