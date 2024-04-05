# USER DETAILS

CREATE TABLE user
(
    id       INTEGER PRIMARY KEY,
    username VARCHAR(40) NOT NULL,
    password VARCHAR(40) NOT NULL
);

CREATE TABLE authority
(
    id   INTEGER PRIMARY KEY,
    name VARCHAR(40) NOT NULL
);

CREATE TABLE user_authority
(
    user_id      INTEGER NOT NULL,
    authority_id INTEGER NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT fk_authority_id FOREIGN KEY (authority_id) REFERENCES authority (id),
    PRIMARY KEY (user_id, authority_id)
);

INSERT INTO user
VALUES (1, "fern", "1234"),
       (2, "frieren", "4321");

INSERT INTO authority
VALUES (1, "read"),
       (2, "write");

INSERT INTO user_authority
VALUES (1, 1),
       (2, 1),
       (2, 2);


# REGISTERED CLIENT
# WARNING: you removed all 'CONSTRAINT fk_registered_client_id' so it won't have conflicts.

CREATE TABLE registered_client
(
    id            VARCHAR(40) PRIMARY KEY,
    client_id     VARCHAR(100) NOT NULL,
    client_secret VARCHAR(100) NOT NULL
);

CREATE TABLE redirect_uri
(
    id                   INTEGER PRIMARY KEY,
    uri                  VARCHAR(255) NOT NULL,
    registered_client_id VARCHAR(40)  NOT NULL,
    FOREIGN KEY (registered_client_id) REFERENCES registered_client (id)
);

CREATE TABLE scope
(
    id   INTEGER PRIMARY KEY,
    name VARCHAR(40) NOT NULL
);

CREATE TABLE registered_client_scope
(
    registered_client_id VARCHAR(40) NOT NULL,
    scope_id             INTEGER     NOT NULL,
    FOREIGN KEY (registered_client_id) REFERENCES registered_client (id),
    FOREIGN KEY (scope_id) REFERENCES scope (id),
    PRIMARY KEY (registered_client_id, scope_id)
);

CREATE TABLE client_authentication_method
(
    id   INTEGER PRIMARY KEY,
    name VARCHAR(40) NOT NULL
);

CREATE TABLE registered_client_client_authentication_method
(
    registered_client_id            VARCHAR(40) NOT NULL,
    client_authentication_method_id INTEGER     NOT NULL,
    FOREIGN KEY (registered_client_id) REFERENCES registered_client (id),
    FOREIGN KEY (client_authentication_method_id) REFERENCES client_authentication_method (id),
    PRIMARY KEY (registered_client_id, client_authentication_method_id)
);

CREATE TABLE authorization_grant_type
(
    id   INTEGER PRIMARY KEY,
    name VARCHAR(40)
);

CREATE TABLE registered_client_authorization_grant_type
(
    registered_client_id        VARCHAR(40) NOT NULL,
    authorization_grant_type_id INTEGER     NOT NULL,
    FOREIGN KEY (registered_client_id) REFERENCES registered_client (id),
    FOREIGN KEY (authorization_grant_type_id) REFERENCES authorization_grant_type (id),
    PRIMARY KEY (registered_client_id, authorization_grant_type_id)
);

INSERT INTO registered_client
VALUES ("{id}SADJAKNDUAI", "client", "secret"),
       ("{id}HJTRFBNYSCV", "reactClient", "GYIOJNVRCJJNCSAYRDNJG");

INSERT INTO redirect_uri
VALUES (1, "https://springone.io/authorized", "{id}SADJAKNDUAI"),
       (2, "http://localhost:8080/oauth2/jwks", "{id}HJTRFBNYSCV");

INSERT INTO scope
VALUES (712, "openid"),
       (713, "profile"),
       (714, "email"),
       (715, "address"),
       (716, "phone");

INSERT INTO registered_client_scope
VALUES ("{id}SADJAKNDUAI", 712),
       ("{id}SADJAKNDUAI", 713),
       ("{id}HJTRFBNYSCV", 712);

INSERT INTO client_authentication_method
VALUES (389, "client_secret_basic"),
       (390, "client_secret_post"),
       (391, "client_secret_jwt"),
       (392, "private_key_jwt"),
       (393, "none");

INSERT INTO registered_client_client_authentication_method
VALUES ("{id}SADJAKNDUAI", 389),
       ("{id}HJTRFBNYSCV", 389);

INSERT INTO authorization_grant_type
VALUES (112, "authorization_code"),
       (113, "client_credentials"),
       (114, "refresh_token");

INSERT INTO registered_client_authorization_grant_type
VALUES ("{id}SADJAKNDUAI", 112),
       ("{id}SADJAKNDUAI", 114),
       ("{id}HJTRFBNYSCV", 112);