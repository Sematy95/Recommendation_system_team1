package pro.sky.recommendation_service.service;

import pro.sky.recommendation_service.dto.RecommendationObject;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {

    Optional<RecommendationObject> getRecommendationObject(UUID userId);


}
