package be.superjoran.mint.dao.custom

import be.superjoran.mint.dao.CategoryExpenseDao
import be.superjoran.mint.domain.searchresults.CategoryExpense
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class CategoryExpenseDaoImpl(private val jdbcTemplate: JdbcTemplate) : CategoryExpenseDao {
    override fun findCategoryExpensesPerYearByOwner(ownerUuid: String): List<CategoryExpense> {
        val sql = "SELECT cat.name as category, sum(s.amount) as sum, date_part('year', s.date) as year\n" +
                "FROM t_statement s\n" +
                "  INNER JOIN t_bankaccount tb ON s.originatingaccount_uuid = tb.uuid\n" +
                "  LEFT OUTER JOIN  t_category cat on s.category_uuid = cat.uuid\n" +
                "  WHERE owner_uuid = ?\n" +
                "GROUP BY cat.name, s.category_uuid, date_part('year', s.date)\n" +
                "ORDER BY year DESC , category"

        return this.jdbcTemplate.query(sql, BeanPropertyRowMapper(CategoryExpense::class.java), ownerUuid)
    }
}
