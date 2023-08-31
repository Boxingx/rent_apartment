CREATE SEQUENCE booking_history_sequence;

CREATE TABLE IF NOT EXISTS booking_history(

    id int8 PRIMARY KEY NOT NULL,
    apartment_id int8 REFERENCES apartment_info(id),
    client_id int8 REFERENCES client_application(id),
    start_date timestamp,
    end_date timestamp,
    product int8 REFERENCES product_info(product_id)

);