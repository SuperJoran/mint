package be.superjoran.mint.dao.custom

import be.superjoran.mint.dao.DestinationCategoryDao
import be.superjoran.mint.domain.searchresults.AutomaticallyAssignOption
import be.superjoran.mint.domain.searchresults.DestinationCategory
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class DestinationCategoryDaoImpl(private val jdbcTemplate: JdbcTemplate) : DestinationCategoryDao {
    override fun findNumberOfStatementsThatCanBeAssigned(personUuid: String): Int {
        val sql = "SELECT count(0) FROM V_STATEMENTS_TO_ASSIGN vc WHERE vc.owner_uuid = ?"

        return this.jdbcTemplate.queryForObject(sql, Int::class.java, personUuid)
    }

    override fun assignCategoriesAutomatically(personUuid: String) {
        val sql = "UPDATE t_statement \n" +
                "SET category_uuid = vc.category_uuid\n" +
                "FROM V_STATEMENTS_TO_ASSIGN vc\n" +
                "WHERE t_statement.uuid = vc.uuid\n" +
                "AND vc.owner_uuid = ?"

        this.jdbcTemplate.update(sql, personUuid)
    }

    override fun findDestinationCategories(): List<DestinationCategory> {
        val sql = "SELECT c.destinationaccount_number AS destinationAccountNumber, c.category_uuid AS categoryUuid FROM V_DESTINATION_CATEGORY c"
        return this.jdbcTemplate.query(sql, BeanPropertyRowMapper(DestinationCategory::class.java))
    }

    override fun assignInternalCategory(ownerUuid: String) {
        val sql = "UPDATE t_statement\n" +
                "SET category_uuid = (SELECT cat.uuid\n" +
                "                     FROM t_category cat\n" +
                "                     WHERE cat.name = 'Internal')\n" +
                "WHERE uuid IN (\n" +
                "  WITH personalAccountNumbers AS (SELECT ba.number\n" +
                "                                  FROM t_bankaccount ba\n" +
                "                                  WHERE ba.owner_uuid = ?),\n" +
                "      personalAccountNumbers_asIng AS (SELECT iban_to_ing_number(ba.number)\n" +
                "                                       FROM t_bankaccount ba\n" +
                "                                       WHERE ba.owner_uuid = ?),\n" +
                "      personalAccountNumbers_unformatted AS (SELECT replace(ba.number, ' ', '')\n" +
                "                                             FROM t_bankaccount ba\n" +
                "                                             WHERE ba.owner_uuid = ?)\n" +
                "  SELECT s.uuid\n" +
                "  FROM t_statement s\n" +
                "    INNER JOIN t_bankaccount ba ON ba.owner_uuid = ?\n" +
                "  WHERE s.destinationaccount_number IN (SELECT *\n" +
                "                                        FROM personalAccountNumbers_asIng)\n" +
                "        OR s.destinationaccount_number IN (SELECT *\n" +
                "                                           FROM personalAccountNumbers)\n" +
                "        OR s.destinationaccount_number IN (SELECT *\n" +
                "                                           FROM personalAccountNumbers_unformatted))"
        this.jdbcTemplate.update(sql, ownerUuid, ownerUuid, ownerUuid, ownerUuid)

    }

    override fun findCategoriesThatHavePossibleCandidates(ownerUuid: String): List<AutomaticallyAssignOption> {
        val sql = "SELECT cat.name as category, count(vc.uuid) as count\n" +
                "FROM V_STATEMENTS_TO_ASSIGN vc\n" +
                "  INNER JOIN t_category cat on cat.uuid = vc.category_uuid\n" +
                "  WHERE vc.owner_uuid = ?\n" +
                "GROUP BY cat.name"

        return this.jdbcTemplate.query(sql, BeanPropertyRowMapper(AutomaticallyAssignOption::class.java), ownerUuid)
    }


}
