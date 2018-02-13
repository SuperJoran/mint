-------------------------------------------------
-- @author: jorandeboever
-- @since: 12/02/18
-------------------------------------------------
CREATE OR REPLACE VIEW V_DESTINATION_CATEGORY AS (
  SELECT
    s1.destinationaccount_number AS destinationAccountNumber,
    (SELECT c2.UUID
     FROM t_category c2
       INNER JOIN t_statement s2 ON c2.uuid = s2.category_uuid
     WHERE c2.UUID = s2.category_uuid AND s2.destinationaccount_number = s1.destinationaccount_number
     GROUP BY c2.uuid
     HAVING count(s2.uuid) >
            (SELECT count(s3.uuid)
             FROM t_statement s3
               INNER JOIN t_category c3 ON s3.category_uuid = c3.uuid
            ) / 2)               AS categoryUuid
  FROM t_statement s1
  WHERE s1.destinationaccount_number != ''
  GROUP BY s1.destinationaccount_number
  HAVING count(s1.uuid) > 10);