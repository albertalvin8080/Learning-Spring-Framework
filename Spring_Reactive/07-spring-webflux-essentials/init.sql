CREATE SCHEMA webflux;

CREATE TABLE webflux.product
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(40)    NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

INSERT INTO webflux.product
    (name, price)
VALUES ('Television', 500.00),
       ('Sofa', 200.00),
       ('Working PC', 1000.00);

CREATE TABLE webflux.user
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    roles    VARCHAR(100)
);

INSERT INTO webflux.user
    (username, password, roles)
VALUES ('albert', '{bcrypt}$2a$10$bkcVnzQRXX7ZqMJso9f5oOfEKTw4Ecus3h4NrXqxjh1PVFLAmtZbC', 'ROLE_USER,ROLE_ADMIN'),
       ('lucas', '{bcrypt}$2a$10$bkcVnzQRXX7ZqMJso9f5oOfEKTw4Ecus3h4NrXqxjh1PVFLAmtZbC', 'ROLE_USER');