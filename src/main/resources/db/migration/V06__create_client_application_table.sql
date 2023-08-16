CREATE SEQUENCE client_application_sequence;

CREATE TABLE IF NOT EXISTS client_application (
    id int8 PRIMARY KEY NOT NULL,
    nick_name varchar(100),
    login_mail varchar(200),
    password varchar(100),
    commercial_status boolean default false,
    booking_count int4,
    parent_city varchar
)


