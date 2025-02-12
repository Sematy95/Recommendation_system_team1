package pro.sky.recommendation_service.service;

import org.springframework.cache.annotation.Cacheable;
import pro.sky.recommendation_service.domain.ResponseForUser;
import pro.sky.recommendation_service.domain.Transaction;

import java.util.List;
import java.util.UUID;

/**
 * Interface defining the contract for retrieving user recommendations.
 */
public interface RecommendationService {
    /**
     * Retrieves recommendations for a user by ID.
     * The results are cached using the "recommendationCache" cache.
     *
     * @param user_id The UUID of the user.
     * @return A ResponseForUser object containing the recommendations.
     */
    @Cacheable(value = "recommendationCache")
    ResponseForUser getRecommendations(UUID user_id);

    /**
     * Retrieves recommendations for a user by username.
     * The results are cached using the "recommendationCache" cache.
     *
     * @param username The username of the user.
     * @return A ResponseForUser object containing the recommendations.
     */
    @Cacheable(value = "recommendationCache")
    ResponseForUser getRecommendationsByUsername(String username);

    /**
     * Retrieves the transaction history for a user by ID.
     *
     * @param user_id The UUID of the user.
     * @return A list of Transaction objects representing the user's transaction history.
     */
    List<Transaction> getTransaction(UUID user_id);
}
