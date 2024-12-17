-- CREATE SEQUENCE IF NOT EXISTS company_seq
--     START WITH 1
--     INCREMENT BY 1
--     NO MINVALUE
--     NO MAXVALUE
--     CACHE 1;

CREATE TABLE IF NOT EXISTS company (
--     company_id INT PRIMARY KEY DEFAULT nextval('company_seq'),
    company_id SERIAL PRIMARY KEY,
    company_name VARCHAR(100) NOT NULL
);

-- select * from company ORDER BY company_name DESC LIMIT 10;
-- ' is being used as the Delimiter
CREATE OR REPLACE FUNCTION populate_db()
    RETURNS VOID AS '
DECLARE
    count INT := 1;
    result INT := 0;
BEGIN
    SELECT count(*) INTO result FROM company;
    IF result = 0 THEN
        FOR count IN 1..100000 LOOP
                INSERT INTO company (company_name)
                VALUES (concat(''company '', count));
            END LOOP;
    END IF;
END;
'
LANGUAGE plpgsql;

SELECT populate_db();