package pro.sky.recommendation_service.service.impl;

import org.springframework.stereotype.Component;
import pro.sky.recommendation_service.dto.RecommendationObject;
import pro.sky.recommendation_service.service.RecommendationRuleSet;

import java.util.UUID;

@Component
public class RecommendationRuleSetImpl implements RecommendationRuleSet {
    @Override
    public RecommendationObject getRecommendationObject(UUID userId) {
        return null;
    }
}
