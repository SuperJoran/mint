-------------------------------------------------
-- @author: jorandeboever
-- @since: 17/03/18
-------------------------------------------------
CREATE FUNCTION iban_to_ing_number(p1 VARCHAR(19))
  RETURNS VARCHAR AS $$ SELECT CONCAT(SUBSTR(REPLACE(p1, ' ', ''), 5, 3), '-', SUBSTR(REPLACE(p1, ' ', ''), 8, 7), '-', SUBSTR(REPLACE(p1, ' ', ''), 15, 2)) $$
LANGUAGE SQL;