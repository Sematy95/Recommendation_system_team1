package pro.sky.recommendation_service.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendation_service.domain.Condition;
import pro.sky.recommendation_service.domain.DynamicRule;
import pro.sky.recommendation_service.domain.DynamicRuleMapper;
import pro.sky.recommendation_service.domain.Transaction;

import java.util.*;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Transaction> getTransactions(UUID user_ID) {
        return jdbcTemplate.query(
                "SELECT t.type as transaction_type, p.type as product_type, t.amount " +
                        "FROM transactions t " +
                        "LEFT JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ?",
                (rs, rowNum) -> new Transaction(rs.getString("transaction_type"),
                        rs.getString("product_type"),
                        rs.getInt("amount")),
                user_ID);
    }
    public void createTemporaryTransactionTableForUser(UUID user_ID) {
        jdbcTemplate.execute("""
                DROP TABLE IF EXISTS TT;
                CREATE TEMPORARY TABLE TT NOT PERSISTENT AS
                SELECT t.TYPE as TRANSACTION_TYPE, t.AMOUNT as TRANSACTION_AMOUNT, p.TYPE as PRODUCT_TYPE, p.NAME as PRODUCT_NAME
                FROM TRANSACTIONS t JOIN PRODUCTS p ON t.PRODUCT_ID=p.ID where USER_ID='
                """ + user_ID + "';");

        }

    public Collection <DynamicRule> createDynamicRules(String sql) {
        Collection<DynamicRule> dynamicRules = new ArrayList<>(jdbcTemplate.query(sql, new DynamicRuleMapper()));
        return dynamicRules;
        }


    }

