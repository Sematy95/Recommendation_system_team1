package pro.sky.recommendation_service.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendation_service.domain.Transaction;

import java.util.UUID;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Transaction getTransaction(UUID user_ID, UUID product_ID) {
        String transactionType = jdbcTemplate.queryForObject(
                "SELECT type FROM transactions t WHERE t.user_id = ? LIMIT 1",
                String.class,
                user_ID);
        Integer amount = jdbcTemplate.queryForObject(
                "SELECT amount FROM transactions t WHERE t.user_id = ? LIMIT 1",
                Integer.class,
                user_ID);
        String productType = jdbcTemplate.queryForObject(
                "SELECT name FROM products p WHERE p.id = ? LIMIT 1",
                String.class,
                product_ID);

        Transaction transaction = new Transaction(transactionType, productType, amount);
        return transaction;
    }
}
