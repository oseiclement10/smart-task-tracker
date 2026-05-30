package console;

import models.tasks.enums.PriorityLevel;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {


    public static Optional<String> validateByPattern(String input, String regexPattern, String patternDesc) {
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            return Optional.empty();
        }

        return Optional.of("Invalid input. Expected format " + patternDesc);
    }

    public static Optional<String> validateDateInput(String dateInput) {
        return validateByPattern(dateInput, "[0-9]{4}-[0-9]{2}-[0-9]{2}", "yyyy-mm-dd");
    }


    public static Optional<String> validateNotEmpty(String userInput, String fieldName) {
        if (userInput == null || userInput.trim().isEmpty()) {
            return Optional.of(fieldName + " Input cannot be empty");
        }

        return Optional.empty();
    }

    public static Optional<String> validatePriorityLevel(String priorityLevelInput) {
        try {
            PriorityLevel.valueOf(priorityLevelInput);
            return Optional.empty();
        } catch (IllegalArgumentException e) {
            return Optional.of(priorityLevelInput + " is not a valid priority level ");
        }
    }



}