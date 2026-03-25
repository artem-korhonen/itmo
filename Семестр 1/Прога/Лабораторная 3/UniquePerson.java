public class UniquePerson extends Person {
    String name = null;
    Talent talent = null;
    City city = null;
    Street street = null;
    House house = null;
    Room room = null;

    public UniquePerson(String name, Talent talent) {
        this.name = name;
        this.talent = talent;
    }

    public void seeAction(String action) {
        System.out.println(this.name + " увидел(а), что " + action);
    }

    public void getInterested(String object) {
        System.out.println(object + " заинтересовал(а) " + this.name);
    }

    @Override
    public void changeCity(City city) {
        this.city = city;
        System.out.println(this.name + " приехал в город " + city.name);
        city.addCitizen(name);
    }

    public void changeStreet(Street street) {
        try {
            if (city == null) {
                throw new AddressException("!!! Город не обозначен, но обозначена улица !!!");
            }
            this.street = street;
            System.out.println(this.name + " поселился на улице " + street.name);
        } catch (AddressException e) {
            System.out.println(e.getMessage());
            this.changeCity(street.city);
            this.changeStreet(street);
        }
    }

    public void changeHouse(House house) {
        try {
            if (street == null) {
                throw new AddressException("!!! Улица не обозначена, но обозначен дом !!!");
            }
            this.house = house;
            System.out.println(this.name + " поселился в доме №" + house.number);
        } catch (AddressException e) {
            System.out.println(e.getMessage());
            this.changeStreet(house.street);
            this.changeHouse(house);
        }
    }

    public void changeRoom(Room room) {
        try {
            if (this.house == null) {
                throw new AddressException("!!! Дом не обозначен, но обозначена комната в доме !!!");
            }
            this.room = room;
            System.out.println(this.name + " поселился(лась) в комнате №" + room.number() + " на " + room.floor() + "-м этаже");
        } catch (AddressException e) {
            System.out.println(e.getMessage());
            this.changeHouse(room.house());
            this.changeRoom(room);
        }
    }

    @Override
    public String toString() {
        if (this.city == null) {
            return this.name + " - " + this.talent.name;
        } else if (this.street == null) {
            return this.name + " - " + this.talent.name + ", который живёт в городе";
        } else if (this.house == null) {
            return this.name + " - " + this.talent.name + ", который живёт в городе " + this.city.name + " на улице " + this.street.name;
        } else if (this.room == null) {
            return this.name + " - " + this.talent + ", который живёт в городе " + this.city.name + " на улице " + this.street.name + " в доме №" + this.house.number;
        } else {
            return this.name + " - " + this.talent.name + ", который живёт в городе " + this.city.name + " на улице " + this.street.name + " в доме №" + this.house.number + " в комнате " + this.room.number() + " на " + this.room.floor() + "-м этаже";
        }
    }

    @Override
    public int hashCode() {
        if (this.city == null) {
            return this.name.length() + this.talent.name.length();
        } else if (this.street == null) {
            return this.name.length() + this.talent.name.length() + this.city.hashCode();
        } else if (this.house == null) {
            return this.name.length() + this.talent.name.length() + this.city.hashCode() + this.street.hashCode();
        } else if (this.room == null) {
            return this.name.length() + this.talent.name.length() + this.city.hashCode() + this.street.hashCode() + this.house.hashCode();
        } else {
            return this.name.length() + this.talent.name.length() + this.city.hashCode() + this.street.hashCode() + this.house.hashCode() + this.room.hashCode();
        }
    }

    public boolean equals(UniquePerson person) {
        return this.name.equals(person.name) && this.talent.equals(person.talent) && this.city.equals(person.city) && this.street.equals(person.street) && this.house.equals(person.house) && this.room.equals(person.room);
    }
}