public enum Talent {
    MUSICIAN("музыкант"),
    WRITER("писатель"),
    ARTIST("художник"),
    ACTOR("актёр"),
    SINGER("певец");

    public String name;

    Talent (String name) {
        this.name = name;
    }
}