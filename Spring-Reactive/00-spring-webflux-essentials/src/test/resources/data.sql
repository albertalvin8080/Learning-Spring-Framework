-- Remember to drop the products.product table inside schema.sql because of:
--      @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD).
-- Otherwise, there will be many repeated insertions.

-- INSERT INTO webflux.product (name, price)
-- VALUES ('Chocco', 234.2),
--        ('Whopper', 0.99),
--        ('SnapDragon', 2000.0);

-- INSERT INTO webflux.appUser
-- (username, password, roles)
-- VALUES ('albert', '{bcrypt}$2a$10$bkcVnzQRXX7ZqMJso9f5oOfEKTw4Ecus3h4NrXqxjh1PVFLAmtZbC', 'ROLE_USER,ROLE_ADMIN'),
--        ('lucas', '{bcrypt}$2a$10$bkcVnzQRXX7ZqMJso9f5oOfEKTw4Ecus3h4NrXqxjh1PVFLAmtZbC', 'ROLE_USER');

INSERT INTO webflux.appUser (username, password, roles)
SELECT 'albert', '{bcrypt}$2a$10$bkcVnzQRXX7ZqMJso9f5oOfEKTw4Ecus3h4NrXqxjh1PVFLAmtZbC', 'ROLE_USER,ROLE_ADMIN'
WHERE NOT EXISTS (
    SELECT 1 FROM webflux.appUser WHERE username = 'albert'
);

INSERT INTO webflux.appUser (username, password, roles)
SELECT 'lucas', '{bcrypt}$2a$10$bkcVnzQRXX7ZqMJso9f5oOfEKTw4Ecus3h4NrXqxjh1PVFLAmtZbC', 'ROLE_USER'
WHERE NOT EXISTS (
    SELECT 1 FROM webflux.appUser WHERE username = 'lucas'
);




