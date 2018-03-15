-------------------------------------------------
-- @author: jorandeboever
-- @since: 15/03/18
-------------------------------------------------
CREATE OR REPLACE VIEW V_STATEMENTS_TO_ASSIGN AS
  SELECT
    s.uuid,
    ba.owner_uuid,
    vc.category_uuid,
    vc.occurences AS certainty
  FROM t_statement s
    INNER JOIN V_DESTINATION_CATEGORY vc ON vc.destinationaccount_number = s.destinationaccount_number
    INNER JOIN t_bankaccount ba ON s.originatingaccount_uuid = ba.uuid
  WHERE s.category_uuid IS NULL;