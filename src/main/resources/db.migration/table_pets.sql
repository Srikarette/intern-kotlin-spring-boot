DROP TABLE IF EXISTS pets;
CREATE TABLE pets
(
    id       UUID        NOT NULL PRIMARY KEY,
    owner_id UUID        NOT NULL,
    name     VARCHAR(50) NOT NULL,
    gender   VARCHAR(10),
    type     varchar(6) -- DOG, CAT, OTHERS
)