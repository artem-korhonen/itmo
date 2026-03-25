package commands;

import java.util.List;

import data.City;

public abstract class Command {
    public abstract List<String> execute(Number number, City city);
}
