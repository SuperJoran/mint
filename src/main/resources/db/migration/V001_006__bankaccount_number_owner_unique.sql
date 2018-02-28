-------------------------------------------------
-- @author: jorandeboever
-- @since: 28/02/18
-------------------------------------------------
ALTER TABLE t_bankaccount
  ADD CONSTRAINT un01_t_bankaccount UNIQUE (number, owner_uuid);
