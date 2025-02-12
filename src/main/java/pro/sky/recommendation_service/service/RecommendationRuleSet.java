package pro.sky.recommendation_service.service;

import pro.sky.recommendation_service.dto.RecommendationObject;

import java.util.Optional;
import java.util.UUID;

/**
 * Interface defining the contract for retrieving recommendation rule sets.
 */
public interface RecommendationRuleSet {
    /**
     * Get recommendations for a given user ID.
     *
     * @param userId The UUID of the user for whom recommendations are requested.
     * @return A list of RecommendationObject instances representing the recommendations.
     */
    Optional<RecommendationObject> getRecommendationObject(UUID userId);
}
