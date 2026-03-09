CREATE TABLE address
(
    id           UUID PRIMARY KEY,
    street       VARCHAR(255) NOT NULL,
    number       VARCHAR(20)  NOT NULL,
    complement   VARCHAR(100),
    neighborhood VARCHAR(100) NOT NULL,
    city         VARCHAR(100) NOT NULL,
    state        VARCHAR(2)   NOT NULL,
    zip_code     VARCHAR(10)  NOT NULL,
    country      VARCHAR(100) NOT NULL
);


