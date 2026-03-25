package main;

import java.util.ArrayList;
import java.util.List;

public class IdGenerator {
    private static Integer newId = 1;
    private static List<Integer> exceptions = new ArrayList<Integer>();

    public static Integer generateId() {
        while (exceptions.contains(newId)) {
            newId++;
        }
        exceptions.add(newId);
        return newId;
    }

    public static void addException(int id) {
        exceptions.add(id);
    }
}
