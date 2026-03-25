package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import data.Government;
import exceptions.StringConvertException;

public class StringConverter {
    public static <T> T convertString(String input, Class<T> type, boolean canNull) throws StringConvertException {
        if (input == null || input.trim().isEmpty()) {
            if (canNull) {
                return null;
            } else {
                throw new StringConvertException("Error: Input cannot be empty or null.");
            }
        }

        if (type == String.class) {
            return type.cast(input);
        } else if (type == Integer.class) {
            try {
                return type.cast(Integer.parseInt(input));
            } catch (NumberFormatException e) {
                throw new StringConvertException("Error: Invalid input for Integer. Please enter a valid integer (e.g., 42 or -99).");
            }
        } else if (type == Double.class) {
            try {
                return type.cast(Double.parseDouble(input));
            } catch (NumberFormatException e) {
                throw new StringConvertException("Error: Invalid input for decimal number. Please enter a valid decimal number (e.g., 123.45 or 0.67).");
            }
        } else if (type == Float.class) {
            try {
                return type.cast(Float.parseFloat(input));
            } catch (NumberFormatException e) {
                throw new StringConvertException("Error: Invalid input for float. Please enter a valid float number (e.g., 3.14 or 0.1).");
            }
        } else if (type == Long.class) {
            try {
                return type.cast(Long.parseLong(input));
            } catch (NumberFormatException e) {
                throw new StringConvertException("Error: Invalid input for Long. Please enter a valid integer (e.g., 42 or -99).");
            }
        } else if (type == Government.class) {
            input = input.toUpperCase().replace(" ", "_");
            boolean governmentTest = false;

            for (String governmentName : new String[] {"PUPPET_STATE", "MERITOCRACY", "PATRIARCHY", "THALASSOCRACY", "ETHNOCRACY"}) {
                if (governmentName.equals(input)) {
                    governmentTest = true;
                    break;
                }
            }

            if (governmentTest) {
                Government government = Government.valueOf(input);
                return type.cast(government);
            } else {
                throw new StringConvertException("Error: Invalid input for Government. Allowed values: Puppet State, Meritocracy, Patriarchy, Thalassocracy, Ethnocracy.");
            }
        } else if (type == LocalDate.class) {
            try {
                return type.cast(LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            } catch (DateTimeParseException e) {
                throw new StringConvertException("Error: Invalid input for LocalDate. Please enter a valid date in format YYYY-MM-DD");
            }
        } else {
            throw new StringConvertException("Error: Unsupported type: " + type.getName());
        }
    }   
}