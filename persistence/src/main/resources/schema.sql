CREATE TABLE IF NOT EXISTS users (
  userid BIGSERIAL PRIMARY KEY,
  username varchar(100),
  password varchar(100),
  profileImage varchar(100)
);

CREATE TABLE IF NOT EXISTS factories (
  userid BIGINT,
  type INT,
  amount DOUBLE PRECISION,
  inputReduction DOUBLE PRECISION,
  outputMultiplier DOUBLE PRECISION,
  costReduction DOUBLE PRECISION,
  level INT
);

CREATE TABLE IF NOT EXISTS wealths (
  userid BIGINT,
  resourceType INT,
  production DOUBLE PRECISION,
  storage DOUBLE PRECISION,
  lastUpdated BIGINT
)