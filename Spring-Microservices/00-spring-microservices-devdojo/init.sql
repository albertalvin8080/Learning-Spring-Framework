CREATE SCHEMA microservices;

CREATE TABLE microservices.product
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(40)    NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

INSERT INTO microservices.product
    (name, price)
VALUES ('Television', 500.00),
       ('Sofa', 200.00),
       ('Working PC', 1000.00);
