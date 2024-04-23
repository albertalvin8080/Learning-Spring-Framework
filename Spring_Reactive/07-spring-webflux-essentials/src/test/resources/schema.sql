-- Beware of re-creation conflicts because of:
--      @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

CREATE SCHEMA IF NOT EXISTS webflux;

-- DROP TABLE IF EXISTS products.product;

CREATE TABLE IF NOT EXISTS webflux.product
(
    id    INTEGER PRIMARY KEY AUTO_INCREMENT,
    name  VARCHAR(30)    NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS webflux.appUser
(
    id       INTEGER PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    roles    VARCHAR(100) NULL
);