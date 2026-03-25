public class House implements Repairable {
    int number;
    int floors;
    String feature;
    Room[] rooms;
    Street street;

    public House(int number, int floors, String feature, Street street, Room[] rooms) {
        this.number = number;
        this.floors = floors;
        this.feature = feature;
        this.street = street;
        this.rooms = rooms;
    }

    @Override
    public String repair(String subject) {
        return "В доме №" + this.number + " на улице " + this.street.name + " ремонтируется " + subject;
    }

    @Override
    public String toString() {
        return "Это дом №" + this.number + ", находящийся на улице " + this.street + ", в котором " + this.floors + " этажей";
    }

    @Override
    public int hashCode() {
        return this.number*1000 + this.floors*100 + this.feature.length()*10 + this.street.hashCode() + this.rooms.length;
    }

    public boolean equals(House house) {
        return this.number == house.number && this.floors == house.floors && this.feature.equals(house.feature) && this.rooms.equals(house.rooms);
    }
}