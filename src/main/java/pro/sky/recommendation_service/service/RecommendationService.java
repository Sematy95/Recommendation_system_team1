package pro.sky.recommendation_service.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Service;
import pro.sky.recommendation_service.dto.RecommendationObject;
import pro.sky.recommendation_service.service.impl.Invest500RuleSet;
import pro.sky.recommendation_service.service.impl.SimpleLoanRuleSet;
import pro.sky.recommendation_service.service.impl.TopSavingRuleSet;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final Map<String, RecommendationRuleSet> recommendationRuleSets;

    public RecommendationService(Map<String, RecommendationRuleSet> recommendationRuleSetMap) {
        recommendationRuleSets = new HashMap<>(recommendationRuleSetMap);
    }

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
