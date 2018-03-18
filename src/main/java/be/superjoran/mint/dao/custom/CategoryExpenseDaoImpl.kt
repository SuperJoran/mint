package be.superjoran.mint.dao.custom

import be.superjoran.mint.dao.CategoryExpenseDao
import be.superjoran.mint.domain.searchresults.CategoryExpense
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class CategoryExpenseDaoImpl(private val jdbcTemplate: JdbcTemplate) : CategoryExpenseDao {
    override fun findCategoryExpensesPerYearByOwner(ownerUuid: String): List<CategoryExpense> {
        val sql = "SELECT\n" +
                "  cat.name                   AS category,\n" +
                "  sum(s.amount)              AS sum,\n" +
                "  date_trunc('year', s.date) as simpleDate\n" +
                "FROM t_statement s\n" +
                "  INNER JOIN t_bankaccount tb ON s.originatingaccount_uuid = tb.uuid\n" +
                "  LEFT OUTER JOIN t_category cat ON s.category_uuid = cat.uuid\n" +
                "  WHERE owner_uuid = ?\n" +
                "GROUP BY cat.name, s.category_uuid, date_trunc('year', s.date)\n" +
                "ORDER BY category, simpleDate DESC"

        return this.jdbcTemplate.query(sql, BeanPropertyRowMapper(CategoryExpense::class.java), ownerUuid)
    }

    override fun findCategoryExpensesPerMonthByOwner(ownerUuid: String): List<CategoryExpense> {
        val sql = "SELECT\n" +
                "  cat.name                   AS category,\n" +
                "  sum(s.amount)              AS sum,\n" +
                "  date_trunc('month', s.date) as simpleDate\n" +
                "FROM t_statement s\n" +
                "  INNER JOIN t_bankaccount tb ON s.originatingaccount_uuid = tb.uuid\n" +
                "  LEFT OUTER JOIN t_category cat ON s.category_uuid = cat.uuid\n" +
                "  WHERE owner_uuid = ?\n" +
                "GROUP BY cat.name, s.category_uuid, date_trunc('month', s.date)\n" +
                "ORDER BY category, simpleDate DESC"

        return this.jdbcTemplate.query(sql, BeanPropertyRowMapper(CategoryExpense::class.java), ownerUuid)
    }
}
