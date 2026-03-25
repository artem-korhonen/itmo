package data;

import java.time.LocalDate;

public class Human {
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Integer age; // Значение поля должно быть больше 0
    private Integer height; // Значение поля должно быть больше 0
    private LocalDate birthday;

    public Human(String name, Integer age, Integer height, LocalDate birthday) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.birthday = birthday;
    }

    public String getName() {
        return this.name;
    }

    public Integer getAge() {
        return this.age;
    }

    public Integer getHeight() {
        return this.height;
    }

    public java.time.LocalDate getBirthday() {
        return this.birthday;
    }

    @Override
    public String toString() {
        return "Human(name = " + this.name + ", age = " + this.age + ", height = " + this.height + ", birthday = " + this.birthday.toString() + ")";
    }

    public boolean equals(Human human) {
        return this.name.equals(human.getName()) && this.age.equals(human.getAge()) && this.height.equals(human.getHeight()) && this.birthday.equals(human.getBirthday());
    }
}