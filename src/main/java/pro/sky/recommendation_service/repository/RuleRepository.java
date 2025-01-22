package pro.sky.recommendation_service.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendation_service.domain.Rule;
import pro.sky.recommendation_service.domain.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Repository
public class RuleRepository {

    private final JdbcTemplate jdbcTemplate;

    public RuleRepository(@Qualifier("defaultJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addRule(Rule rule) {
        jdbcTemplate.update("INSERT INTO rules (id, query, arguments, negate) VALUES (?, ?, ?, ?)",
                UUID.randomUUID(),
                rule.getQuery(),
                rule.getArguments().toArray(new String[rule.getArguments().size()]),
                rule.isNegate());
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
