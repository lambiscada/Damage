DROP TABLE Client;
CREATE TABLE Client
 (idClient BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
  nameClient VARCHAR(36) NOT NULL,
  PRIMARY KEY(idClient))
