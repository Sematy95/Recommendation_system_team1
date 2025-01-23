package pro.sky.recommendation_service.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendation_service.domain.RequestObject;

import java.util.*;

@Repository
public class RuleRepository  {

    private final JdbcTemplate jdbcTemplate;

    public RuleRepository(@Qualifier("defaultJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addRule(RequestObject requestObject) {
        jdbcTemplate.update("INSERT INTO rules (id, query, arguments, negate) VALUES (?, ?, ?, ?)",
                UUID.randomUUID(),
                requestObject.getQuery(),
                requestObject.getArguments().toArray(new String[requestObject.getArguments().size()]),
                requestObject.isNegate());
    }

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
