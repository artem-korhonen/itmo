package data;

public class Coordinates {
    private float x; //Максимальное значение поля: 774
    private Long y; //Значение поля должно быть больше -497, Поле не может быть null

    public Coordinates(float x, Long y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return this.x;
    }

    public Long getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    public boolean equals(Coordinates coordinates) {
        return (this.x == coordinates.getX()) && this.y.equals(coordinates.getY());
    }
}