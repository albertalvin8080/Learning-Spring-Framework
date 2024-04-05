CREATE TABLE product (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         price DECIMAL(10,2) NOT NULL
);

INSERT INTO product (name, price) VALUES ('T-Shirt', 19.99);
INSERT INTO product (name, price) VALUES ('Coffee Mug', 9.95);
INSERT INTO product (name, price) VALUES ('Laptop', 799.99);
