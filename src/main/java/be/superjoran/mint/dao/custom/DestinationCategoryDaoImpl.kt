package be.superjoran.mint.dao.custom

import be.superjoran.mint.dao.DestinationCategoryDao
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
}
