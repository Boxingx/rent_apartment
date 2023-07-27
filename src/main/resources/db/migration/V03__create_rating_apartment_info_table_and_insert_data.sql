CREATE TABLE IF NOT EXISTS rating_apartment_info (
    id int8 PRIMARY KEY NOT NULL,
    rating int4,
    review varchar(1000),
    apartment_id int8 REFERENCES apartment_info(id)
);

CREATE SEQUENCE rating_info_sequence start 3 increment 1;

INSERT INTO rating_apartment_info
values (1, 7, 'Квартира супер!',10),
       (2, 3, 'Не было убрано', 10);

