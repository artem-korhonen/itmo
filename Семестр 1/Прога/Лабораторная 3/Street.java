public class Street implements Repairable {
    String name;
    House[] houses;
    City city;

    public Street(String name, City city, House[] houses) {
        this.name = name;
        this.city = city;
        this.houses = houses;
    }

    @Override
    public String repair(String subject) {
        return "На улице " + this.name + " ремонтируется " + subject;
    }

    @Override
    public String toString() {
        return this.name + " - улица, находящаяся в городе " + this.city.name;
    }

    @Override
    public int hashCode() {
        return this.houses.length * 50 - this.name.length() * 3 + this.city.hashCode();
    }

    public boolean equals(Street street) {
        return this.name.equals(street.name) && this.houses.equals(street.houses) && this.city.equals(street.city);
    }
}