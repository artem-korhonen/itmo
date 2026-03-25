INSERT INTO Human (name, age, gender) VALUES
('Alice', 22, 'Female'),
('Jack', 21, 'Male'),
('Tom', 22, 'Male');


INSERT INTO Clothing (human_id, name, brand, material, price) VALUES
(1, 'Sweatshirt', 'Sweet Company', 'Cotton', 2999),
(1, 'Jeans', 'Sweet Company', 'Denim', 1899),
(1, 'Sneakers', 'Sports Attack', 'Textile', 7999),
(2, 'Cap', 'Caps Masters', 'Cotton', 1500),
(2, 'T-Shirt', 'DinoPark Merch', 'Cotton', 999),
(2, 'Sport Pants', 'Sports Attack', 'Stretch', 3499),
(2, 'Sneakers', 'Low Price Shop', 'Textile', 800),
(3, 'T-Shirt', 'Official Style Company', 'Cotton', 2899),
(3, 'Shirt', 'Official Style Company', 'Cotton', 6999),
(3, 'Jacket', 'Official Style Company', 'Wool', 11999), 
(3, 'Trousers', 'Official Style Company', 'Flax', 7999), 
(3, 'Shoes', 'Official Style Company', 'Genuine leather', 39999);


INSERT INTO Building (name, year, floors_count) VALUES
('Small Office', 1979, 2),
('Small Office', 1987, 2),
('Cottage', 1898, 1);


INSERT INTO Explore_Buildings (human_id, building_id, operation_code) VALUES
(1, 1, 'ca5525fa-5ad8-44ad-8090-6efa2a76e9d1'),
(1, 2, '60756d2d-e915-41ae-81d0-d8b97121d745'),
(2, 3, '73023522-2efb-48e5-b9f6-601a6172c7ff'),
(3, 1, '530b46c9-143b-402a-a4a0-c65362133d82'),
(3, 3, '8ee8bf3f-4680-4f1f-9b90-f5ea615d0ce7');


INSERT INTO Floor (building_id, number, temperature, rooms_count) VALUES
(1, 1, 22, 2),
(1, 2, 22, 2),
(2, 1, 13, 2),
(2, 2, 11, 2),
(3, 1, 23, 2);


INSERT INTO Room (floor_id, name, number, temperature, humidity) VALUES
(1, 'Office room 1', 11, 23, 48),
(1, 'Office room 2', 12, 22, 47),
(2, 'Office room 3', 21, 22, 47),
(2, 'Director room', 22, 22, 48),
(3, 'Office room 1', 1, 12, 73),
(3, 'Office room 2', 2, 12, 73),
(4, 'Office room 3', 3, 10, 74),
(4, 'Director room', 4, 11, 76),
(5, 'Kitchen / Living Room', 0, 23, 37),
(5, 'Bedroom', 0, 23, 37); 


INSERT INTO Machinery (room_id, name, company, is_active) VALUES
(1, 'Computer', 'PacBook', TRUE),
(1, 'Printer', 'Paper Lovers', TRUE),
(2, 'Computer', 'PacBook', TRUE),
(2, 'Laptop', 'PacBook', FALSE),
(2, 'Printer', 'Paper Lovers', FALSE),
(3, 'Computer', 'PacBook', FALSE),
(3, 'Printer', 'Paper Lovers', TRUE),
(4, 'Computer', 'PacBook', TRUE),
(4, 'Laptop', 'Premium PC', TRUE),
(4, 'Printer', 'Paper Lovers', TRUE), 
(5, 'Computer', 'BomPC', FALSE), 
(5, 'Printer', 'Office Scan Systems', FALSE), 
(6, 'Computer', 'BomPC', FALSE), 
(6, 'Printer', 'Office Scan Systems', FALSE), 
(7, 'Computer', 'BomPC', FALSE), 
(7, 'Printer', 'Office Scan Systems', FALSE), 
(8, 'Computer', 'BomPC', FALSE), 
(8, 'Computer', 'PacBook', FALSE), 
(8, 'Laptop', 'PickMeBook', FALSE), 
(8, 'Printer', 'Office Scan Systems', FALSE), 
(9, 'Microwave', 'SMS Kitchen Inc.', TRUE), 
(9, 'Fridge', 'SMS Kitchen Inc.', TRUE), 
(9, 'Multicooker', 'SMS Kitchen Inc.', TRUE), 
(10, 'Lamp', 'My love is book', FALSE), 
(10, 'Lamp', 'Light Life', TRUE); 


INSERT INTO Furniture (room_id, name, material, height) VALUES
(1, 'Table', 'Wood', 0.8),
(1, 'Bookshelf', 'Wood', 2.5),
(2, 'Table', 'Wood', 0.8),
(2, 'Bookshelf', 'Wood', 2.5),
(3, 'Table', 'Wood', 0.8),
(3, 'Bookshelf', 'Wood', 2.5),
(4, 'Table', 'Wood', 0.8),
(4, 'Bookshelf', 'Wood', 2.5),
(5, 'Table', 'Wood', 0.97),
(5, 'Bookshelf', 'Wood', 2.8), 
(6, 'Table', 'Wood', 0.97), 
(6, 'Bookshelf', 'Wood', 2.8), 
(7, 'Table', 'Wood', 0.97), 
(7, 'Bookshelf', 'Wood', 2.8), 
(8, 'Table', 'Wood', 0.97), 
(8, 'Bookshelf', 'Wood', 2.8), 
(9, 'Table', 'Wood', 0.74),
(9, 'Wardrobe', 'Wood', 2.8),
(9, 'Shelf', 'Wood', 0.6),
(10, 'Bed', 'Wood', 0.4),
(10, 'Bed', 'Wood', 0.4),
(10, 'Bed', 'Wood', 0.4),
(10, 'Night table', 'Wood', 0.5),
(10, 'Night table', 'Wood', 0.5);


INSERT INTO Plant (room_id, name, height, alive) VALUES
(1, 'Ficus', 1.2, TRUE),
(2, 'Cactus', 0.2, TRUE),
(3, 'Cactus', 0.3, TRUE),
(4, 'Ficus', 1.3, TRUE),
(4, 'Cactus', 0.17, TRUE),
(5, 'Geranium', 0.27, FALSE),
(6, 'Ficus', 1.33, FALSE),
(7, 'Ficus', 1.1, FALSE),
(9, 'Rose', 0.32, TRUE),
(9, 'Cactus', 0.41, TRUE);