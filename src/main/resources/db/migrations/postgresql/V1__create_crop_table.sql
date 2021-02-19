CREATE TABLE crops_quotations (
  id        SERIAL              PRIMARY KEY,
  code      VARCHAR(255)        NOT NULL,
  price     DOUBLE PRECISION    NOT NULL,
  date      VARCHAR(10)         NOT NULL
);
