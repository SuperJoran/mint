-------------------------------------------------
-- @author: jorandeboever
-- @since: 17/03/18
-------------------------------------------------
DROP VIEW V_STATEMENTS_TO_ASSIGN;
DROP VIEW v_destination_category;

CREATE VIEW v_destination_category AS
  WITH categories AS (
      SELECT
        s1.destinationaccount_number,
        count(category_uuid) AS occurences,
        (SELECT s2.category_uuid
         FROM t_statement s2
         WHERE ((s2.destinationaccount_number) :: TEXT = (s1.destinationaccount_number) :: TEXT)
               AND 1 > (SELECT COUNT(s3.category_uuid)
                        FROM t_statement s3
                        WHERE ((s2.destinationaccount_number) :: TEXT = (s3.destinationaccount_number) :: TEXT) AND s3.category_uuid != S2.category_uuid)
         GROUP BY s2.category_uuid
         ORDER BY (count(0))
         LIMIT 1
        )                    AS category_uuid
      FROM t_statement s1
      WHERE ((s1.category_uuid IS NOT NULL) AND ((s1.destinationaccount_number) :: TEXT <> '' :: TEXT))
      GROUP BY s1.destinationaccount_number
  )
  SELECT
    categories.destinationaccount_number,
    categories.occurences,
    categories.category_uuid
  FROM categories
  WHERE (categories.occurences > 1 AND (category_uuid IS NOT NULL));

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

