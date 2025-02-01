package pro.sky.recommendation_service.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendation_service.domain.Transaction;

import java.util.List;
import java.util.UUID;

/**
 * Repository class for accessing transaction data from the database.
 */
@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Get a list of transactions for a given user ID.
     *
     * @param user_ID   The UUID of the user for whom transactions are to be retrieved.
     * @return A list of Transaction objects representing the user's transactions.
     */
    public List<Transaction> getTransactions(UUID user_ID) {
        return jdbcTemplate.query(
                "SELECT t.type as transaction_type, p.type as product_type, t.amount " +
                        "FROM transactions t " +
                        "LEFT JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ?",
                (rs, rowNum) -> new Transaction(rs.getString("transaction_type"), rs.getString("product_type"), rs.getInt("amount")),
                user_ID);
    }
}
