-- Remember to drop the products.product table inside schema.sql because of:
--      @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD).
-- Otherwise, there will be many repeated insertions.

-- INSERT INTO webflux.product (name, price)
-- VALUES ('Chocco', 234.2),
--        ('Whopper', 0.99),
--        ('SnapDragon', 2000.0);

INSERT INTO webflux.appUser
(username, password, roles)
VALUES ('albert', '{bcrypt}$2a$10$bkcVnzQRXX7ZqMJso9f5oOfEKTw4Ecus3h4NrXqxjh1PVFLAmtZbC', 'ROLE_USER,ROLE_ADMIN'),
       ('lucas', '{bcrypt}$2a$10$bkcVnzQRXX7ZqMJso9f5oOfEKTw4Ecus3h4NrXqxjh1PVFLAmtZbC', 'ROLE_USER');