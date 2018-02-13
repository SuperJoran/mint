package be.superjoran.mint.dao.custom;

import be.superjoran.mint.dao.DestinationCategoryDao;
import be.superjoran.mint.domain.searchresults.DestinationCategory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DestinationCategoryDaoImpl implements DestinationCategoryDao {

    private final JdbcTemplate jdbcTemplate;

    public DestinationCategoryDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<DestinationCategory> findDestinationCategories() {
        String sql = "SELECT c.destinationaccountnumber as destinationAccountNumber, c.categoryuuid as categoryUuid FROM V_DESTINATION_CATEGORY c";
        return this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DestinationCategory.class));
    }
}
