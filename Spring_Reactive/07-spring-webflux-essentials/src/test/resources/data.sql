-- Remember to drop the products.product table inside schema.sql because of:
--      @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD).
-- Otherwise, there will be many repeated insertions.

-- INSERT INTO webflux.product (name, price)
-- VALUES ('Chocco', 234.2),
--        ('Whopper', 0.99),
--        ('SnapDragon', 2000.0);