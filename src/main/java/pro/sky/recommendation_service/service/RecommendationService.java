package pro.sky.recommendation_service.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Service;
import pro.sky.recommendation_service.dto.RecommendationObject;
import pro.sky.recommendation_service.service.impl.Invest500RuleSet;
import pro.sky.recommendation_service.service.impl.SimpleLoanRuleSet;
import pro.sky.recommendation_service.service.impl.TopSavingRuleSet;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class forgetting recommendations.
 */
@Service
public class RecommendationService {
    private final Map<String, RecommendationRuleSet> recommendationRuleSets;

    public RecommendationService(Map<String, RecommendationRuleSet> recommendationRuleSetMap) {
        recommendationRuleSets = new HashMap<>(recommendationRuleSetMap);
    }

    /**
     * Retrieves a list of recommendations for a given user.
     *
     * @param user_id The unique ID of the user for whom recommendations are to be retrieved.
     * @return A list of RecommendationObject instances representing the recommendations.
     *         Returns an empty list if no recommendations are found for the user.
     */
    // todo create interface
    @Cacheable
    public List<RecommendationObject> getRecommendations(UUID user_id) {
        List<RecommendationObject> recommendationObjects = new ArrayList<>();
        for (RecommendationRuleSet recommendationRuleSet : recommendationRuleSets.values()) {
            if (recommendationRuleSet.getRecommendationObject(user_id).isPresent()) {
                recommendationObjects.add(recommendationRuleSet.getRecommendationObject(user_id).get());
            }
        }
        return recommendationObjects;
    }
}
