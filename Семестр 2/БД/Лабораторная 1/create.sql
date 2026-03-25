CREATE TABLE Human (
    human_id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    age INTEGER CHECK (age >= 0 AND age <= 200),
    gender VARCHAR(50) NOT NULL,
    max_rooms_to_explore INTEGER
);

CREATE TABLE Clothing (
    clothing_id SERIAL PRIMARY KEY,
    human_id INTEGER REFERENCES Human(human_id) ON DELETE CASCADE,
    name VARCHAR(50) NOT NULL,
    brand VARCHAR(50) NOT NULL,
    material VARCHAR(50) NOT NULL,
    price INTEGER CHECK (price >= 0)
);

CREATE TABLE Building (
    building_id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    street VARCHAR(50),
    number INTEGER,
    year INTEGER CHECK (year >= 0),
    floors_count INTEGER CHECK (floors_count > 0 AND floors_count <= 1000)
);

CREATE TABLE Explore_Buildings (
    id SERIAL PRIMARY KEY,
    human_id INTEGER REFERENCES Human(human_id) ON DELETE CASCADE,
    building_id INTEGER REFERENCES Building(building_id) ON DELETE CASCADE,
    operation_code VARCHAR(50) NOT NULL UNIQUE,
    explore_date DATE
);

CREATE TABLE Floor (
    floor_id SERIAL PRIMARY KEY,
    building_id INTEGER REFERENCES Building(building_id) ON DELETE CASCADE,
    number INTEGER CHECK (number >= 0),
    temperature NUMERIC(4,1) CHECK (temperature >= -273.15 AND temperature <= 1000),
    rooms_count INTEGER CHECK (rooms_count >= 0 AND rooms_count <= 1000)
);

CREATE TABLE Room (
    room_id SERIAL PRIMARY KEY,
    floor_id INTEGER REFERENCES Floor(floor_id) ON DELETE CASCADE,
    name VARCHAR(50) NOT NULL,
    number INTEGER CHECK (number >= 0),
    temperature NUMERIC(4,1) CHECK (temperature >= -273.15 AND temperature <= 1000),
    humidity INTEGER CHECK (humidity >= 0 AND humidity <= 100)
);

CREATE TABLE Machinery (
    machinery_id SERIAL PRIMARY KEY,
    room_id INTEGER REFERENCES Room(room_id) ON DELETE CASCADE,
    name VARCHAR(50) NOT NULL,
    company VARCHAR(50) NOT NULL,
    is_active BOOLEAN
);

CREATE TABLE Furniture (
    furniture_id SERIAL PRIMARY KEY,
    room_id INTEGER REFERENCES Room(room_id) ON DELETE CASCADE,
    name VARCHAR(50) NOT NULL,
    material VARCHAR(50) NOT NULL,
    height FLOAT CHECK (height > 0 AND height <= 10)
);

CREATE TABLE Plant (
    plant_id SERIAL PRIMARY KEY,
    room_id INTEGER REFERENCES Room(room_id) ON DELETE CASCADE,
    name VARCHAR(50) NOT NULL,
    height FLOAT CHECK (height > 0 AND height <= 10),
    alive BOOLEAN
);
