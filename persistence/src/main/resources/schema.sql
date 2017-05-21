CREATE TABLE IF NOT EXISTS clans (
  clanId SERIAL PRIMARY KEY,
  name   VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
  userid BIGSERIAL PRIMARY KEY,
  username varchar(100) UNIQUE ,
  password varchar(100),
  profileImage varchar(100),
  score DOUBLE PRECISION,
  clanId INT,
  FOREIGN KEY (clanId) REFERENCES clans(clanId) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS factories (
  userid BIGINT,
  type INT,
  amount DOUBLE PRECISION,
  inputReduction DOUBLE PRECISION,
  outputMultiplier DOUBLE PRECISION,
  costReduction DOUBLE PRECISION,
  level INT,
  PRIMARY KEY(userid, type)
);

CREATE TABLE IF NOT EXISTS wealths (
  userid BIGINT,
  resourceType INT,
  production DOUBLE PRECISION,
  storage DOUBLE PRECISION,
  lastUpdated BIGINT,
  PRIMARY KEY(userid, resourceType)
);

CREATE TABLE IF NOT EXISTS stockMarket (
  resourceType INT PRIMARY KEY ,
  amount DOUBLE PRECISION
);

select factories.userid from (factories left outer join users ON factories.userid = users.userid) where username IS NULL;
