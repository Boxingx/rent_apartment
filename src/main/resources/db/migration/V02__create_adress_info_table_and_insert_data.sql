CREATE TABLE IF NOT EXISTS address_info (
                                            id int8 PRIMARY KEY NOT NULL,
                                            city varchar,
                                            street varchar,
                                            building_number varchar,
                                            apartments_number varchar,
                                            apartment_id int8 REFERENCES apartment_info(id)
);

CREATE SEQUENCE address_info_sequence start 16 increment 1;

INSERT INTO address_info (id, city, street, building_number, apartments_number, apartment_id)
VALUES(1, 'Москва', 'Ленина', '25' , '42', 1),
      (2, 'Санкт-Петербург', 'Площадь мира', '22б' , '95', 2),
      (3, 'Омск', 'Космический проспект', '13' , '27', 3),
      (4, 'Тюмень', 'Жукова', '14' , '156', 4),
      (5, 'Омск', 'Кордная', '19' , '5', 5),
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