-------------------------------------------------
-- @author: jorandeboever
-- @since: 11/02/18
-------------------------------------------------
ALTER TABLE t_bankaccount
  DROP COLUMN administrator_uuid;

ALTER TABLE t_statement ALTER COLUMN csv_line TYPE VARCHAR(4000) USING csv_line::VARCHAR(4000);