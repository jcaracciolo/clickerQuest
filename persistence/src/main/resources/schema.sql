--TODO Delete this
DROP TABLE users;

CREATE TABLE IF NOT EXISTS users (
  userid BIGSERIAL PRIMARY KEY,
  username varchar(100),
  password varchar(100)
);

CREATE TABLE IF NOT EXISTS factories (
  userid BIGINT,
  type INT,
  amount DOUBLE PRECISION,
  level INT,
  inputReduction DOUBLE PRECISION,
  outputMultiplier DOUBLE PRECISION,
  costReduction DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS wealths (
  userid BIGINT,
  resourceType INT,
  production DOUBLE PRECISION,
  storage DOUBLE PRECISION,
  lastUpdated DATE
)