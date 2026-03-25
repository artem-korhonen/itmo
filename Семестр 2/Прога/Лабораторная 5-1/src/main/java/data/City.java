package data;

import java.time.LocalDate;

public class City {
    private Integer id; // Поле не может быть null, Значение поля должно быть больше 0, Значение этого
                        // поля должно быть уникальным
                        // Значение этого поля должно генерироваться автоматически
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private LocalDate creationDate; // Поле не может быть null,
                                              // Значение этого поля должно генерироваться автоматически
    private int area; // Значение поля должно быть больше 0
    private Integer population; // Значение поля должно быть больше 0, Поле не может быть null
    private Double metersAboveSeaLevel;
    private Float timezone; // Значение поля должно быть больше -13, Максимальное значение поля: 15
    private long populationDensity; // Значение поля должно быть больше 0
    private Government government; // Поле может быть null
    private Human governor; // Поле не может быть null


    public City(Integer id, String name, Coordinates coordinates, int area,
            Integer population, Double metersAboveSeaLevel, Float timezone, long populationDensity,
            Government government, Human governor) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.timezone = timezone;
        this.populationDensity = populationDensity;
        this.government = government;
        this.governor = governor;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public java.time.LocalDate getCreationDate() {
        return this.creationDate;
    }

    public int getArea() {
        return this.area;
    }

    public Integer getPopulation() {
        return this.population;
    }

    public Double getMetersAboveSeaLevel() {
        return this.metersAboveSeaLevel;
    }

    public Float getTimezone() {
        return this.timezone;
    }

    public long getPopulationDensity() {
        return this.populationDensity;
    }

    public Government getGovernment() {
        return this.government;
    }

    public Human getGovernor() {
        return this.governor;
    }

    @Override
    public String toString() {
        return "City(" + "id = " + this.id + ", name = " + this.name + ", coordinates = " + this.coordinates.toString()
                + ", creationDate = " + this.creationDate.toString() + ", area = " + this.area + ", population = "
                + this.population + ", metersAboveSeaLevel = " + this.metersAboveSeaLevel + ", timezone = "
                + this.timezone + ", populationDensity = " + this.populationDensity + ", Government = "
                + this.government.toString() + ", governor = " + this.governor.toString() + ")";
    }

    @Override
    public int hashCode() {
        return (int) (this.id + this.name.length() + Math.round(this.coordinates.getX())
                + Math.round(this.coordinates.getY()) + this.area + this.population + Math.round(this.timezone)
                + Math.round(this.metersAboveSeaLevel) + Math.round(this.populationDensity)
                + Math.round(this.government.toString().length()) * 100 + this.governor.getAge());
    }

    public boolean equals(City city) {
        return (this.id.equals(city.getId()) && this.name.equals(city.getName())
                && this.coordinates.equals(city.getCoordinates())
                && this.creationDate.equals(city.getCreationDate()) && this.area == city.area
                && this.population.equals(city.getPopulation())
                && this.metersAboveSeaLevel.equals(city.getMetersAboveSeaLevel())
                && this.timezone.equals(city.getTimezone()) && this.populationDensity == city.getPopulationDensity()
                && this.government.equals(city.getGovernment()) && this.governor.equals(city.getGovernor()));
    }
}
