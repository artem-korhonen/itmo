package transport;

import java.io.Serializable;

import data.City;
import user.Profile;


public class Request implements Serializable {
    private String commandName;
    private Number number;
    private City city;
    private Profile profile;

    public Request(String commandName, Number number, City city, Profile profile) {
        this.commandName = commandName;
        this.number = number;
        this.city = city;
        this.profile = profile;
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

    public Profile getProfile() {
        return this.profile;
    }
}
