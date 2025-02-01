package pro.sky.recommendation_service.service;

import pro.sky.recommendation_service.dto.RecommendationObject;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    // Get recommendationObject by userId
    /**
     * Get recommendations for a given user ID.
     *
     * @param userId    The UUID of the user for whom recommendations are requested.
     * @return A list of RecommendationObject instances representing the recommendations.
     */
    Optional<RecommendationObject> getRecommendationObject(UUID userId);
}
