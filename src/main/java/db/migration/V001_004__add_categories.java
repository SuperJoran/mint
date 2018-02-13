package db.migration;

import be.superjoran.mint.domain.CategoryType;
import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class V001_004__add_categories implements JdbcMigration {
    @Override
    public void migrate(Connection connection) throws Exception {

        String insertCategoryGroupSql = "INSERT INTO t_category_group (uuid, category_type, name) VALUES (?, ?, ?)";
        String insertCategorySql = "INSERT INTO t_category (uuid, name, category_group_uuid) VALUES (?, ?, (SELECT UUID FROM t_category_group gr WHERE gr.name = ?))";

        Map<String, List<String>> map = new HashMap<>();
        map.put("Car & Transport", Arrays.asList("Fuel", "Taxes", "Insurance", "Upkeep"));
        map.put("Household", Collections.singletonList("Food"));
        map.put("Healthcare", Arrays.asList("Docter", "Pharmacy", "Mutuality"));
        map.put("Clothing & Personal Care", Arrays.asList("Hairdresser", "Clothing"));
        map.put("Entertainment", Arrays.asList("Games", "Movies"));
        map.put("Telecom", Arrays.asList("Phone", "Subscription"));
        map.put("Other", Collections.singletonList("Internal"));


        try (
                PreparedStatement insertCategoryGroupStatement = connection.prepareStatement(insertCategoryGroupSql);
                PreparedStatement insertCategoryStatement = connection.prepareStatement(insertCategorySql)
        ) {
            for (Entry<String, List<String>> entry : map.entrySet()) {
                insertCategoryGroupStatement.setString(1, UUID.randomUUID().toString());
                insertCategoryGroupStatement.setString(2, CategoryType.EXPENSE.toString());
                insertCategoryGroupStatement.setString(3, entry.getKey());
                insertCategoryGroupStatement.addBatch();

                for (String category : entry.getValue()) {
                    insertCategoryStatement.setString(1, UUID.randomUUID().toString());
                    insertCategoryStatement.setString(2, category);
                    insertCategoryStatement.setString(3, entry.getKey());
                    insertCategoryStatement.addBatch();
                }
            }

            insertCategoryGroupStatement.executeBatch();
            insertCategoryStatement.executeBatch();
        }
    }
}
