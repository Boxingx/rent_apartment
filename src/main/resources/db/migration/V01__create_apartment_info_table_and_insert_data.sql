CREATE TABLE IF NOT EXISTS apartment_info (
                                              id int8 PRIMARY KEY NOT NULL,
                                              rooms_count varchar,
                                              average_rating varchar,
                                              price varchar,
                                              status varchar,
                                              registration_date varchar
);

CREATE SEQUENCE apartment_info_sequence start 3 increment 1;

INSERT INTO apartment_info (id, rooms_count, average_rating, price, status, registration_date)
VALUES(1, '4', null,'5000','true', null),
      (2, '3', null,'4000','false', null),
      (3, '2', null,'2700','true', null),
      (4, '3', null,'2500','false', null),
      (5, '4', null,'3200','true', null),
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