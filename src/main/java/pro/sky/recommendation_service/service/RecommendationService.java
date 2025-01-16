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

    private final List<RecommendationRuleSet> recommendationRuleSets;

    public RecommendationService(@Qualifier("Invest500RuleSet") Invest500RuleSet invest500RuleSet,
                                 @Qualifier("SimpleLoanRuleSet") SimpleLoanRuleSet simpleLoanRuleSet,
                                 @Qualifier("TopSavingRuleSet")TopSavingRuleSet topSavingRuleSet) {
        recommendationRuleSets = new ArrayList<>();
        recommendationRuleSets.add(invest500RuleSet);
        recommendationRuleSets.add(simpleLoanRuleSet);
        recommendationRuleSets.add(topSavingRuleSet);
    }

    public List<RecommendationObject> getRecommendations(UUID user_id) {

        List<RecommendationObject> recommendationObjects = new ArrayList<>();
        for (RecommendationRuleSet recommendationRuleSet : recommendationRuleSets) {
            if (recommendationRuleSet.getRecommendationObject(user_id).isPresent()) {
                recommendationObjects.add(recommendationRuleSet.getRecommendationObject(user_id).get());
            }
        }

        return recommendationObjects;
    }
}
