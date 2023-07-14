CREATE TABLE IF NOT EXISTS apartment_info (
    id int8 PRIMARY KEY NOT NULL,
    rooms_count varchar,
    average_rating varchar,
    price varchar,
    status varchar,
    registration_date varchar
);

CREATE SEQUENCE apartment_info_sequence start 3 increment 1;

CREATE TABLE IF NOT EXISTS address_info (
    id int8 PRIMARY KEY NOT NULL,
    city varchar,
    street varchar,
    building_number varchar,
    apartments_number varchar,
    apartment_id int8 REFERENCES apartment_info(id)
);

CREATE SEQUENCE address_info_sequence start 3 increment 1;

INSERT INTO apartment_info (id, rooms_count, average_rating, price, status, registration_date)
VALUES(1, '4', null,'5000','true', null),
      (2, '3', null,'4000','false', null);

INSERT INTO address_info (id, city, street, building_number, apartments_number, apartment_id)
VALUES(1, 'Москва', 'Ленина', '25' , '42', 1),
      (2, 'Санкт-Петербург', 'Площадь мира', '22б' , '95', 2);


INSERT INTO apartment_info (id, rooms_count, average_rating, price, status, registration_date)
VALUES(3, '2', null,'2700','true', null),
      (4, '3', null,'2500','false', null);

INSERT INTO address_info (id, city, street, building_number, apartments_number, apartment_id)
VALUES(3, 'Омск', 'Космический проспект', '13' , '27', 3),
      (4, 'Тюмень', 'Жукова', '14' , '156', 4);

INSERT INTO apartment_info (id, rooms_count, average_rating, price, status, registration_date)
VALUES(5, '4', null,'3200','true', null),
      (6, '1', null,'2000','false', null),
      (7, '5', null,'4000','false', null),
      (8, '3', null,'3000','false', null),
      (9, '2', null,'2200','false', null),
      (10, '1', null,'1800','false', null),
      (11, '4', null,'3600','false', null),
      (12, '3', null,'3100','false', null),
      (13, '2', null,'2250','false', null),
      (14, '2', null,'2300','false', null),
      (15, '1', null,'1900','false', null);


INSERT INTO address_info (id, city, street, building_number, apartments_number, apartment_id)
VALUES(5, 'Омск', 'Кордная', '19' , '5', 5),
      (6, 'Новосибирск', 'Маяковского', '1' , '24', 6),
      (7, 'Новосибирск', 'Хмельницкого', '13' , '18', 7),
      (8, 'Новосибирск', 'Нефтезаводская', '45' , '70', 8),
      (9, 'Омск', 'Лицкевича', '56' , '24', 9),
      (10, 'Красноярск', 'Грачева', '15' , '98', 10),
      (11, 'Краснодар', 'Столовая', '3' , '85', 11),
      (12, 'Махачкала', 'Инженерная', '5' , '53', 12),
      (13, 'Пенза', 'Рабочая', '17' , '95', 13),
      (14, 'Самара', 'Маркса', '27' , '56', 14),
      (15, 'Тверь', 'Тверская', '15' , '3', 15);




