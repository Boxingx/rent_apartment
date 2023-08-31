CREATE SEQUENCE product_info_sequence;

CREATE TABLE IF NOT EXISTS product_info (
    product_id int8 PRIMARY KEY NOT NULL,
    product_name varchar,
    discount int8,
    discount_type varchar,
    description varchar,
    status varchar
);

INSERT INTO product_info
VALUES
(1, 'Первая аренда', 15, 'Процент', 'Скидка 15% если пользователь еще ниразу не арендовал квартиру', 'true'),
(2, 'Пятая аренда', 12, 'Процент', 'Скидка 12% если пользователь пользовался сервисом пять или больше раз', 'true'),
(3, 'Десятая аренда', 17, 'Процент', 'Скидка 17% если пользователь пользовался сервисом десять или больше раз', 'true'),
(4, 'Для своих', 10, 'Процент', 'Скидка 10% если город пользователя и город аренды совпадают', 'true'),
(5, 'Пять дней аренды', 5, 'Процент', 'Скидка 5% если пользователь арендовал квартиру на 5 или больше дней', 'true'),
(6, 'Десять дней аренды', 10, 'Процент', 'Скидка 10% если пользователь арендовал квартиру на 10 или больше дней', 'true'),
(7, 'Промокод', 5, 'Процент', 'Скидка 5% если пользователь ввел промокод', 'true'),
(8, 'Зимовка', 7, 'Зима', 'Скидка 7% если пользователь арендует квартиру в зимнее время года', 'false'),
(9, 'Реферальная ссылка', 10, 'Процент', 'Скидка 10% если пользователь зарегистрировался по реферальной ссылке,хранится 3 месяца', 'true')
