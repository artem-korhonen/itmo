package main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import data.City;
import data.Coordinates;
import data.Government;
import data.Human;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager(String url, String user, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, user, password);
    }

    public void executeUpdate(String sql, Object... params) throws SQLException {
        try (PreparedStatement state = connection.prepareStatement(sql)) {
            setParams(state, params);
            state.executeUpdate();
        }
    }

    public ResultSet executeSelect(String sql, Object... params) throws SQLException {
        PreparedStatement state = connection.prepareStatement(sql);
        setParams(state, params);
        return state.executeQuery();
    }


    private void setParams(PreparedStatement state, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            state.setObject(i + 1, params[i]);
        }
    }

    public boolean register(String username, String password) throws SQLException {
        ResultSet testUserResultSet = this.executeSelect("SELECT * FROM users WHERE username = (?)", username);
        boolean testUser = testUserResultSet.next();
        
        testUserResultSet.getStatement().close();
        testUserResultSet.close();

        if (testUser) {  
            return false;
        } else {
            this.executeUpdate("INSERT INTO users (username, password) VALUES (?, ?)", username, password);
            return true;
        }
    }

    public boolean login(String username, String password) throws SQLException {
        ResultSet testUserResultSet = this.executeSelect("SELECT * FROM users WHERE username = (?) AND password = (?)", username, password);
        boolean testUser = testUserResultSet.next();
        
        testUserResultSet.getStatement().close();
        testUserResultSet.close();
        return testUser;
    }

    public void loadCity(City city) throws SQLException {
        Human governor = city.getGovernor();
        this.executeUpdate(
            "INSERT INTO people (name, age, height, birthday) VALUES (?, ?, ?, ?)",
            governor.getName(),
            governor.getAge(),
            governor.getHeight(),
            governor.getBirthday()
        );

        ResultSet governorsTable = this.executeSelect(
            "SELECT * FROM people WHERE name = (?) AND age = (?) AND height = (?) AND birthday = (?) ORDER BY id DESC",
            governor.getName(), governor.getAge(), governor.getHeight(), governor.getBirthday()
        );

        if (!governorsTable.next()) {
            throw new SQLException("Error");
        }
        int governorId = governorsTable.getInt("id");

        ResultSet users = this.executeSelect("SELECT id FROM users WHERE username = (?)", city.getUsername());
        if (!users.next()) {
            throw new SQLException("Error: User hasn't found " + city.getUsername());
        }
        int userId = users.getInt("id");
        this.executeUpdate(
            "INSERT INTO cities (id, user_id, name, coord_x, coord_y, creation_date, area, population, meters_above_sea_level, timezone, population_density, government, governor_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            city.getId(),
            userId,
            city.getName(),
            city.getCoordinates().getX(),
            city.getCoordinates().getY(),
            city.getCreationDate(),
            city.getArea(),
            city.getPopulation(),
            city.getMetersAboveSeaLevel(),
            city.getTimezone(),
            city.getPopulationDensity(),
            city.getGovernment().name(),
            governorId
        );
        System.out.println("✓ Город успешно добавлен в таблицу cities");

        governorsTable.getStatement().close();
        governorsTable.close();
        users.getStatement().close();
        users.close();
    }


    public List<City> getCities() throws SQLException {
        ResultSet citiesTable = this.executeSelect("SELECT * FROM cities INNER JOIN people ON cities.governor_id = people.id");
        List<City> cities = new ArrayList<>();
        
        while (citiesTable.next()) {
            Human governor = new Human(
                citiesTable.getString("name"), 
                citiesTable.getInt("age"), 
                citiesTable.getInt("height"), 
                citiesTable.getDate("birthday").toLocalDate()
            );
            Coordinates coordinates = new Coordinates(citiesTable.getFloat("coord_x"), citiesTable.getLong("coord_y"));
            Government government = Government.valueOf(citiesTable.getString("government"));
            ResultSet users = this.executeSelect("SELECT username FROM users WHERE id = (?)", citiesTable.getInt("user_id"));
            users.next();
            String username = users.getString("username");
            City city = new City(
                citiesTable.getInt("id"),
                username,
                citiesTable.getString("name"),
                coordinates,
                citiesTable.getInt("area"),
                citiesTable.getInt("population"),
                citiesTable.getDouble("meters_above_sea_level"),
                citiesTable.getFloat("timezone"),
                citiesTable.getLong("population_density"),
                government,
                governor
            );
            cities.add(city);
        }

        return cities;
    }

    public void clearCities() throws SQLException {
        this.executeUpdate("DELETE FROM cities");
    }

    public void removeCity(int id) throws SQLException {
        this.executeUpdate("DELETE FROM cities WHERE id = (?)", id);
    }

    public void removeCities(List<Integer> citiesIds) throws SQLException{
        String questionsString = citiesIds.stream().map(id -> "?").collect(Collectors.joining(", "));
        this.executeUpdate("DELETE FROM cities WHERE id IN (" + questionsString + ")", citiesIds);
    }

    public void updateCity(Integer cityId, City city) throws SQLException {
        System.out.println(city.toString());
        System.out.println(city.getUsername());

        Human governor = city.getGovernor();
        this.executeUpdate(
            "INSERT INTO people (name, age, height, birthday) VALUES (?, ?, ?, ?)",
            governor.getName(),
            governor.getAge(),
            governor.getHeight(),
            governor.getBirthday()
        );
        ResultSet governorsTable = this.executeSelect("SELECT * FROM people WHERE name = (?) AND age = (?) AND height = (?) AND birthday = (?) ORDER BY id DESC", governor.getName(), governor.getAge(), governor.getHeight(), governor.getBirthday());
        if (!governorsTable.next()) {
            throw new SQLException("Error");
        }
        int governorId = governorsTable.getInt("id");
        ResultSet users = this.executeSelect("SELECT id FROM users WHERE username = (?)", city.getUsername().trim());
        if (!users.next()) {
            throw new SQLException("Error: User hasn't found " + city.getUsername());
        }
        int userId = users.getInt("id");
        this.executeUpdate(
            "UPDATE cities SET user_id = (?), name = (?), coord_x = (?), coord_y = (?), creation_date = (?), area = (?), population = (?), meters_above_sea_level = (?), timezone = (?), population_density = (?), government = (?), governor_id = (?) WHERE id = (?)",
            userId,
            city.getName(),
            city.getCoordinates().getX(),
            city.getCoordinates().getY(),
            city.getCreationDate(),
            city.getArea(),
            city.getPopulation(),
            city.getMetersAboveSeaLevel(),
            city.getTimezone(),
            city.getPopulationDensity(),
            city.getGovernment().name(),
            governorId,
            cityId
        );
    }
}


// CREATE TABLE cities (
//     id INTEGER PRIMARY KEY CHECK (id > 0),
//     user_id INTEGER NOT NULL REFERENCES users(id),
//     name TEXT NOT NULL CHECK (name <> ''),
//     coord_x REAL CHECK (coord_x <= 774),
//     coord_y BIGINT NOT NULL CHECK (coord_y > -497),
//     creation_date DATE NOT NULL DEFAULT CURRENT_DATE,
//     area INTEGER CHECK (area > 0),
//     population INTEGER NOT NULL CHECK (population > 0),
//     meters_above_sea_level DOUBLE PRECISION,
//     timezone REAL CHECK (timezone > -13 AND timezone <= 15),
//     population_density BIGINT CHECK (population_density > 0),
//     government TEXT CHECK (government IN (
//         'PUPPET_STATE',
//         'MERITOCRACY',
//         'PATRIARCHY',
//         'THALASSOCRACY',
//         'ETHNOCRACY'
//     )),
//     governor_id INTEGER NOT NULL REFERENCES people(id)
// );


// CREATE TABLE people (
//     id SERIAL PRIMARY KEY,
//     name TEXT NOT NULL CHECK (name <> ''),
//     age INTEGER CHECK (age > 0),
//     height INTEGER CHECK (height > 0),
//     birthday DATE
// );
