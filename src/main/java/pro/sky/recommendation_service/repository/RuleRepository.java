package pro.sky.recommendation_service.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendation_service.domain.Condition;

import java.util.*;

/**
 * Repository class for managing dynamic rules in the database.
 */
@Repository
public class RuleRepository  {
    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructs a new RuleRepository.
     *
     * @param jdbcTemplate The JdbcTemplate to use for database interactions
     */
    public RuleRepository(@Qualifier("defaultJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    public void addRule(Condition condition) {
//        jdbcTemplate.update("INSERT INTO rules (id, query, arguments, negate) VALUES (?, ?, ?, ?)",
//                UUID.randomUUID(),
//                condition.getQuery(),
//                condition.getArguments().toArray(new String[condition.getArguments().size()]),
//                condition.isNegate());
//    }

    /**
     * Deletes a dynamic rule from the database by its ID.
     *
     * @param id The UUID of the rule to be deleted.
     */
    public void deleteRule(UUID id) {
        jdbcTemplate.update("DELETE FROM rules WHERE id = ?",
                id);
    }

//    public List<Rule> getAllRules() {
//        return jdbcTemplate.query("SELECT query, arguments, negate FROM rules",
//                (rs, rowNum) -> new Rule(rs.getString("query"),
//                        new ArrayList<String>(rs.getArray("arguments")),
//                        rs.getBoolean("negate")));
//    }
}
