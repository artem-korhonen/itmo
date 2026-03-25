import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Создание локации

        City flowerCity = new City("Цветочный город", 0, null);

        Street appleSquare = new Street("Яблочная площадь", flowerCity, null);
        Street cookieStreet = new Street("Улица печенья", flowerCity, null);
        Street lollipopStreet = new Street("Леденцовая улица", flowerCity, null);

        House storyHouse = new House(10, 3, "Дом, в котором живёт Пуговка со своими подружками", appleSquare, null);
        House basedHouse1 = new House(48, 3, "Обычный дом", appleSquare, null);
        House basedHouse2 = new House(12, 3, "Обычный дом", cookieStreet, null);
        House basedHouse3 = new House(31, 3, "Обычный дом", cookieStreet, null);
        House basedHouse4 = new House(33, 3, "Обычный дом", lollipopStreet, null);
        House basedHouse5 = new House(46, 3, "Обычный дом", lollipopStreet, null);

        Room basedRoom1 = new Room(1, 1, "Обычная комната", storyHouse);
        Room storyRoom = new Room(2, 2, "Светлая комната с большим окном", storyHouse);
        Room basedRoom2 = new Room(3, 3, "Обычная комната", storyHouse);
        Room basedRoom3 = new Room(1, 1, "Обычная комната", basedHouse1);
        Room basedRoom4 = new Room(2, 2, "Обычная комната", basedHouse1);
        Room basedRoom5 = new Room(3, 3, "Обычная комната", basedHouse1);
        Room basedRoom6 = new Room(1, 1, "Обычная комната", basedHouse2);
        Room basedRoom7 = new Room(2, 2, "Обычная комната", basedHouse2);
        Room basedRoom8 = new Room(3, 3, "Обычная комната", basedHouse2);
        Room basedRoom9 = new Room(1, 1, "Обычная комната", basedHouse3);
        Room basedRoom10 = new Room(2, 2, "Обычная комната", basedHouse3);
        Room basedRoom11 = new Room(3, 3, "Обычная комната", basedHouse3);
        Room basedRoom12 = new Room(1, 1, "Обычная комната", basedHouse4);
        Room basedRoom13 = new Room(2, 2, "Обычная комната", basedHouse4);
        Room basedRoom14 = new Room(3, 3, "Обычная комната", basedHouse4);
        Room basedRoom15 = new Room(1, 1, "Обычная комната", basedHouse5);
        Room basedRoom16 = new Room(2, 2, "Обычная комната", basedHouse5);
        Room basedRoom17 = new Room(3, 3, "Обычная комната", basedHouse5);

        Street[] streetsForCity = {appleSquare, cookieStreet, lollipopStreet};

        House[] housesForStreet1 = {storyHouse, basedHouse1};
        House[] housesForStreet2 = {basedHouse2, basedHouse3};
        House[] housesForStreet3 = {basedHouse4, basedHouse5};

        Room[] roomsForHouse1 = {basedRoom1, storyRoom, basedRoom2};
        Room[] roomsForHouse2 = {basedRoom3, basedRoom4, basedRoom5};
        Room[] roomsForHouse3 = {basedRoom6, basedRoom7, basedRoom8};
        Room[] roomsForHouse4 = {basedRoom9, basedRoom10, basedRoom11};
        Room[] roomsForHouse5 = {basedRoom12, basedRoom13, basedRoom14};
        Room[] roomsForHouse6 = {basedRoom15, basedRoom16, basedRoom17};

        flowerCity.streets = streetsForCity;

        appleSquare.houses = housesForStreet1;
        cookieStreet.houses = housesForStreet2;
        lollipopStreet.houses = housesForStreet3;

        storyHouse.rooms = roomsForHouse1;
        basedHouse1.rooms = roomsForHouse2;
        basedHouse2.rooms = roomsForHouse3;
        basedHouse3.rooms = roomsForHouse4;
        basedHouse4.rooms = roomsForHouse5;
        basedHouse5.rooms = roomsForHouse6;

        // Создание персонажей

        UniquePerson Tyubik = new UniquePerson("Тюбик", Talent.ARTIST);
        UniquePerson Guslya = new UniquePerson("Гусля", Talent.MUSICIAN);

        OrdinaryPerson[] ordinaryPersons = {
            new OrdinaryPerson("Ваня", "", flowerCity),
            new OrdinaryPerson("Вова", "", flowerCity),
            new OrdinaryPerson("Петя", "", flowerCity),
            new OrdinaryPerson("Коля", "", flowerCity),
            new OrdinaryPerson("Аня", "", flowerCity),
            new OrdinaryPerson("Оля", "", flowerCity)
        };

        flowerCity.citizensCount += ordinaryPersons.length;

        // Начало действий

        Tyubik.changeCity(flowerCity);
        Guslya.changeCity(flowerCity);

        for (int i = 0; i < ordinaryPersons.length; i++) {
            ordinaryPersons[i].findOut(Tyubik.name + " - " + Tyubik.talent.name + " и " + Guslya.name + " - " + Guslya.talent.name);
        }

        for (int i = 0; i < ordinaryPersons.length; i++) {
            ordinaryPersons[i].changeDesire("послушать игру на флейте");
        }

        Random rn = new Random();
        Tyubik.seeAction(cookieStreet.repair("дорога"));
        House randomHouse = lollipopStreet.houses[rn.nextInt(lollipopStreet.houses.length)];
        Guslya.seeAction(randomHouse.repair("крыша"));

        Tyubik.changeHouse(storyHouse);
        Guslya.changeHouse(storyHouse);

        for (int i = 0; i < ordinaryPersons.length; i++) {
            ordinaryPersons[i].findOut(Tyubik.name + " поселился(лась) в доме на улице " + Tyubik.house.street.name + " №" + Tyubik.house.number + " и особенностью " + Tyubik.house.feature);
            ordinaryPersons[i].findOut(Guslya.name + " поселился(лась) в доме на улице " + Guslya.house.street.name + " №" + Guslya.house.number + " и особенностью " + Guslya.house.feature);
        }

        Tyubik.getInterested(storyRoom.feature());

        Tyubik.changeRoom(storyRoom);
        Guslya.changeRoom(storyRoom);
    }
}