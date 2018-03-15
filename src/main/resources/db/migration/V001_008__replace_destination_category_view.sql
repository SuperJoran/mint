DROP VIEW V_DESTINATION_CATEGORY;
CREATE OR REPLACE VIEW V_DESTINATION_CATEGORY AS (
  WITH categories AS (
      SELECT
        s1.destinationaccount_number,
        count(0)  AS occurences,
        (SELECT s2.category_uuid
         FROM t_statement s2
         WHERE s2.destinationaccount_number = s1.destinationaccount_number
         GROUP BY s2.category_uuid
         ORDER BY COUNT(0)
         LIMIT 1) AS category_uuid
      FROM t_statement s1
      WHERE category_uuid IS NOT NULL AND destinationaccount_number != ''
      GROUP BY s1.destinationaccount_number
  )
  SELECT *
  FROM categories
  WHERE categories.occurences > 1)