package pro.sky.recommendation_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.recommendation_service.domain.Transaction;
import pro.sky.recommendation_service.dto.RecommendationObject;
import pro.sky.recommendation_service.repository.RecommendationsRepository;
import pro.sky.recommendation_service.service.RecommendationService;

import java.util.List;
import java.util.UUID;

@RestController
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/recommendation/{user_id}")
    public List<RecommendationObject> getRecommendation(@PathVariable("user_id") UUID id) {
        return recommendationService.getRecommendations(id);
    }
}
