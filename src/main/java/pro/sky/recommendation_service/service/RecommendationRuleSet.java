package pro.sky.recommendation_service.service;

import pro.sky.recommendation_service.dto.RecommendationObject;

import java.util.UUID;

public interface RecommendationRuleSet {

    RecommendationObject getRecommendationObject(UUID userId);


}
