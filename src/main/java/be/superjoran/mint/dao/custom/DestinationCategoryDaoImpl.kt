package be.superjoran.mint.dao.custom

import be.superjoran.mint.dao.DestinationCategoryDao
import be.superjoran.mint.domain.searchresults.DestinationCategory
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class DestinationCategoryDaoImpl(private val jdbcTemplate: JdbcTemplate) : DestinationCategoryDao {

    override fun findDestinationCategories(): List<DestinationCategory> {
        val sql = "SELECT c.destinationaccountnumber as destinationAccountNumber, c.categoryuuid as categoryUuid FROM V_DESTINATION_CATEGORY c"
        return this.jdbcTemplate.query(sql, BeanPropertyRowMapper(DestinationCategory::class.java))
    }
}
