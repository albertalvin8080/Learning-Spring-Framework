CREATE SCHEMA microservices;

CREATE TABLE microservices.product
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(40)    NOT NULL,
    price DECIMAL(10, 3) NOT NULL
);

INSERT INTO microservices.product
    (name, price)
VALUES ('Television', 500.00),
       ('Sofa', 200.00),
       ('Working PC', 1000.00);

CREATE TABLE microservices.app_user
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(40) NOT NULL,
    password VARCHAR(40) NOT NULL,
    roles    VARCHAR(40) NOT NULL
);

INSERT INTO microservices.app_user
    (username, password, roles)
VALUES ('albert', '$2a$10$QshaUCOtseEDuBCv1SbYi.3En1AzJmZEca/GiPrvZ3kfzedNIudPa', 'ROLE_USER,ROLE_ADMIN'),
       ('lucas', '$2a$10$QshaUCOtseEDuBCv1SbYi.3En1AzJmZEca/GiPrvZ3kfzedNIudPa', 'ROLE_USER');
