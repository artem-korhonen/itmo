package main;

import java.time.LocalDate;
import java.util.Scanner;

import data.City;
import data.Coordinates;
import data.Government;
import data.Human;
import exceptions.StringConvertException;

public class Console {
    private Scanner scanner;
    private boolean active = true;

    public Console() {
        this.scanner = new Scanner(System.in);
    }

    public void startConsole(CommandManager commandManager) {
        try {
            String input;
            while (this.active) {
                System.out.print("> ");
                input = this.scanner.nextLine();
                commandManager.executeCommand(input);
                System.out.println("");
            }
        } finally {
            scanner.close();
        }
    }

    public void breakConsole() {
        this.active = false;
        System.exit(0); // Принудительное завершение
    }

    public <T> T oneInput(String message, Class<T> type, boolean canNull) {
        System.out.println(message);
        while (true) {
            try {
                T input1 = StringConverter.convertString(this.scanner.nextLine(), type, canNull);
                return input1;
            } catch (StringConvertException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public <T> T oneInput(String message, Class<T> type, boolean canNull, T min, T max, boolean strictMin,
            boolean strictMax) {
        System.out.println(message);
        while (true) {
            try {
                T input1 = StringConverter.convertString(this.scanner.nextLine(), type, canNull);

                boolean isValid = false;

                if (type == Integer.class) {
                    Integer inputVal = (Integer) input1;
                    Integer minVal = (Integer) min;
                    Integer maxVal = (Integer) max;
                    isValid = (strictMin ? inputVal > minVal : inputVal >= minVal) &&
                            (strictMax ? inputVal < maxVal : inputVal <= maxVal);
                } else if (type == Float.class) {
                    Float inputVal = (Float) input1;
                    Float minVal = (Float) min;
                    Float maxVal = (Float) max;
                    isValid = (strictMin ? inputVal > minVal : inputVal >= minVal) &&
                            (strictMax ? inputVal < maxVal : inputVal <= maxVal);
                } else if (type == Double.class) {
                    Double inputVal = (Double) input1;
                    Double minVal = (Double) min;
                    Double maxVal = (Double) max;
                    isValid = (strictMin ? inputVal > minVal : inputVal >= minVal) &&
                            (strictMax ? inputVal < maxVal : inputVal <= maxVal);
                } else if (type == Long.class) {
                    Long inputVal = (Long) input1;
                    Long minVal = (Long) min;
                    Long maxVal = (Long) max;
                    isValid = (strictMin ? inputVal > minVal : inputVal >= minVal) &&
                            (strictMax ? inputVal < maxVal : inputVal <= maxVal);
                }

                if (isValid) {
                    return input1;
                } else {
                    throw new StringConvertException(
                            "Error: Invalid input. Please enter a valid value between " + min + " and " + max);
                }
            } catch (StringConvertException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public City cityInput(Integer id) {
        String name = this.oneInput("Name of city", String.class, false);
        float x = this.oneInput("Coordinate x of city (less than or equal to 774)", Float.class, true, Float.MIN_VALUE,
                774f, false, false);
        Long y = this.oneInput("Coordinate y of city (more than -497)", Long.class, false, -497l, Long.MAX_VALUE, true,
                false);
        int area = this.oneInput("Area of city (more than 0)", Integer.class, true, 0, Integer.MAX_VALUE, true, false);
        int population = this.oneInput("Population of city(more than 0)", Integer.class, false, 0, Integer.MAX_VALUE,
                true, false);
        double metersAboveSeaLevel = this.oneInput("Meters above sea level", Double.class, true, Double.MIN_VALUE,
                Double.MAX_VALUE, false, false);
        float timezone = this.oneInput("Timezone of city (more than -13 and less than or equal to 15)", Float.class,
                true, -13f, 15f, true, false);
        long populationDensity = this.oneInput("Population Density of city (more than 0)", Long.class, true, 0l,
                Long.MAX_VALUE, true, false);
        Government government = this.oneInput(
                "Government of city (Puppet State, Meritocracy, Patriarchy, Thalassocrathy, Ethnocracy)",
                Government.class, true);
        String governorName = this.oneInput("Governor name", String.class, false);
        int governorAge = this.oneInput("Governor age (more than 0)", Integer.class, true, 0, Integer.MAX_VALUE, true,
                false);
        int governorHeight = this.oneInput("Governor height (more than 0)", Integer.class, true, 0, Integer.MAX_VALUE,
                true, false);
        LocalDate governorBirthday = this.oneInput("Governor birthday (YYYY-MM-DD)", LocalDate.class, true);
        return new City(id, name, new Coordinates(x, y), area, population, metersAboveSeaLevel, timezone,
                populationDensity, government, new Human(governorName, governorAge, governorHeight, governorBirthday));
    }

}
