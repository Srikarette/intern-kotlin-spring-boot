DROP TABLE IF EXISTS accounts;
CREATE TABLE accounts
(
    id           UUID         NOT NULL PRIMARY KEY,
    first_name   VARCHAR(50)  NOT NULL,
    last_name    VARCHAR(50)  NOT NULL,
    gender       VARCHAR(6)   NOT NULL, --// MALE, FEMALE, OTHERS
    phone_number VARCHAR(10),
    email        VARCHAR(100),
    username     VARCHAR(50),
    password     VARCHAR(255) NOT NULL
);