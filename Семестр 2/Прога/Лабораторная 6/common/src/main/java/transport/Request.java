package transport;

import java.io.Serializable;

import data.City;

public class Request implements Serializable {
    private String commandName;
    private Number number;
    private City city;

    public Request(String commandName, Number number, City city) {
        this.commandName = commandName;
        this.number = number;
        this.city = city;
    }

    public String getCommandName() {
        return this.commandName;
    }

    public Number getNumber() {
        return this.number;
    }

    public City getCity() {
        return this.city;
    }
}
