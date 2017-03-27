SET DATABASE SQL SYNTAX PGS TRUE;
create TABLE if not exists   users (
  userid serial  PRIMARY KEY,
  username varchar(100),
  password varchar(100),
  publicMsg varchar(100)
);

CREATE TABLE IF NOT EXISTS tokens (
  userid INTEGER PRIMARY KEY,
  token  varchar(100) unique
);

SET DATABASE SQL SYNTAX PGS TRUE;

create TABLE if not exists   users (
  userid serial  PRIMARY KEY,
  username varchar(100),
  password varchar(100),
  publicMsg varchar(100)
);

CREATE TABLE IF NOT EXISTS tokens (
  userid INTEGER PRIMARY KEY,
  token  varchar(100) unique
);

SELECT * FROM users;

DROP FUNCTION createUser;
CREATE FUNCTION createUser
	(usernameIN VARCHAR(100),password varchar(100))
    RETURNS table(usernameOUT varchar(100), loginToken text, userIDOut INTEGER, isValid BOOLEAN)
	modifies READS sql DATA BEGIN ATOMIC

		DECLARE userIDOut INTEGER;
		DECLARE loginToken text;
		SET userIDOut = 23;

		IF  (b1 > userIDOut) THEN
		SET userIDOut = 23;
		END IF;

		RETURN table(SELECT usernameIN, password, 1 ,TRUE);
	END;;


SELECT * FROM TABLE( createUser('adsa','ad'));
--
-- CREATE or replace FUNCTION createUser
-- 	(inout usernameIN VARCHAR(100),password varchar(100), out loginToken text,out userIDOut integer, out isValid bool)
--     AS $$
-- begin
-- 	isValid := false;
-- 	loginToken := '';
--
-- 	if not exists(select * from users where username = usernameIN)
-- 	then
-- 		isValid := TRUE;
-- 		INSERT INTO users(username,password) VALUES(usernameIN, password);
-- 		userIDOut := (select userid from users where username = usernameIN);
-- 		loginToken := getLoginToken(userIDOut);
-- 	end if;
-- END; $$ LANGUAGE plpgsql;
--
-- CREATE or replace FUNCTION logIn
-- 	(inout usernameIN VARCHAR(100),passwordIN varchar(100), out loginToken text,out userIDOut integer, out isValid bool)
--     AS $$
-- begin
-- 	isValid := false;
-- 	loginToken := '';
--
-- 	if exists(select * from users where username = usernameIN and password = passwordIN)
-- 	then
-- 		isValid := true;
-- 		userIDOut := (select userid from users where username = usernameIN and password = passwordIN);
-- 		loginToken := getLoginToken(userIDOut);
-- 	end if;
-- END; $$ LANGUAGE plpgsql;
--
-- CREATE or replace FUNCTION tokenIsValid
-- 	(userIdIN integer,tokenIN varchar(100), out isValid bool)
--     AS $$
--     declare storedUserID integer;
-- begin
-- 	isValid := false;
--
-- 	if exists(select * from tokens where userid = userIdIN and token = tokenIN)
-- 	then
-- 		isValid := true;
-- 	end if;
-- END; $$ LANGUAGE plpgsql;
--
--
-- CREATE or replace FUNCTION viewPublicMsg
-- 	(userIdIN integer, out isValid bool, out msg varchar(250))
--     AS $$
-- begin
-- 	isValid := false;
--
-- 	if exists(select * from users where userid = userIdIN)
-- 	then
-- 		isValid := true;
-- 		msg := (select publicMsg from users where userid = userIdIN);
-- 	end if;
-- END; $$ LANGUAGE plpgsql;
--
-- CREATE or replace FUNCTION changePublicMsg
-- 	(userIdIN integer,loginToken varchar(100) ,msg varchar(250), out isValid bool)
--     AS $$
-- begin
-- 	isValid := false;
--
-- 	if tokenIsValid(userIdIN, loginToken)
-- 	then
-- 		isValid := true;
-- 		UPDATE users SET publicMsg = msg where userid = userIdIN;
-- 	end if;
-- END; $$ LANGUAGE plpgsql;
--
