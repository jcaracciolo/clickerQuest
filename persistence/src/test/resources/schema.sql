CREATE TABLE IF NOT EXISTS users (
  userid INTEGER IDENTITY PRIMARY KEY,
  username varchar(100),
  password varchar(100)
);

CREATE TABLE IF NOT EXISTS factories (
  userid BIGINT,
  type INTEGER,
  amount DOUBLE,
  inputReduction DOUBLE,
  outputMultiplier DOUBLE,
  costReduction DOUBLE,
  level INTEGER,
  PRIMARY KEY(userid, type)
);

CREATE TABLE IF NOT EXISTS wealths (
  userid BIGINT ,
  resourceType INTEGER,
  production DOUBLE,
  storage DOUBLE,
  lastUpdated DATE,
  PRIMARY KEY(userid, resourceType)
)