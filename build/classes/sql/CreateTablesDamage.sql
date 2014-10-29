DROP TABLE Damage;
CREATE TABLE Damage
 (idDamage BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
  nameDamage VARCHAR(36) NOT NULL,
  valueIni BIGINT,
  locationDamage VARCHAR(16),
  dateAct DATE,
  description VARCHAR(20),
  levelDamage INT,
  depositIni BIGINT,
  clientName VARCHAR(16),
  PRIMARY KEY(idDamage))

