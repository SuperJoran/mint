-------------------------------------------------
-- @author: jorandeboever
-- @since: 6/03/18
-------------------------------------------------
ALTER TABLE t_statement
  ALTER COLUMN csv_line TYPE VARCHAR(4000);

ALTER TABLE t_statement
  ALTER COLUMN description TYPE VARCHAR(1000);

