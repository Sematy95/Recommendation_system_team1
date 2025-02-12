package pro.sky.recommendation_service.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pro.sky.recommendation_service.domain.Transaction;

import java.util.List;
import java.util.UUID;

/**
 * Repository class for accessing user and transaction data from the database.
 * This class uses JdbcTemplate to interact with the database.
 */
@Repository
// todo interface instead of class may have been implied
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Retrieves the user ID based on the username.
     *
     * @param userName The username to search for.
     * @return The UUID of the user, or null if no user is found.
     */
    // todo refactor userName to username
    public UUID getUserIdByUserName(String userName) {
        String sql = "SELECT id FROM users WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userName}, UUID.class);
    }

    /**
     * Retrieves the full name (first name and last name) of a user based on their username.
     *
     * @param username The username to search for.
     * @return The full name of the user, or null if no user is found.
     */
    public String getFullNameByUsername(String username) {
        String sql = "SELECT first_name, last_name FROM users WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, (rs, rowNum) -> {
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            return firstName + " " + lastName;
        });
    }

    /**
     * Retrieves a list of transactions for a given user ID.
     *
     * @param user_ID The UUID of the user.
     * @return A list of Transaction objects.
     */
    // todo refactor user_ID to userID or userId
    public List<Transaction> getTransactions(UUID user_ID) {
        return jdbcTemplate.query(
                "SELECT t.type as transaction_type, p.type as product_type, t.amount " +
                        "FROM transactions t " +
                        "LEFT JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ?",
                (rs, rowNum) -> new Transaction(rs.getString("product_type"),
                        rs.getString("transaction_type"),
                        rs.getInt("amount")),
                user_ID);
    }
}
