CREATE TABLE user (
    id INTEGER PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    password VARCHAR(40) NOT NULL
);

CREATE TABLE authority (
    id INTEGER PRIMARY KEY,
    name VARCHAR(40) NOT NULL
);

CREATE TABLE user_authority (
    user_id INTEGER NOT NULL,
    authority_id INTEGER NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES user(id),
    CONSTRAINT fk_authority_id FOREIGN KEY (authority_id) REFERENCES authority(id),
    PRIMARY KEY (user_id, authority_id)
);

INSERT INTO user VALUES(1, "alllmer", "12345");
INSERT INTO authority VALUES(1, "read");
INSERT INTO user_authority VALUES(1, 1);