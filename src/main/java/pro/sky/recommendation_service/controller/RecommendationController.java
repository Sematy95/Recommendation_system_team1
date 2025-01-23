package pro.sky.recommendation_service.controller;

import org.springframework.web.bind.annotation.*;
import pro.sky.recommendation_service.dto.RecommendationObject;
import pro.sky.recommendation_service.repository.RuleRepository;
import pro.sky.recommendation_service.service.RecommendationService;

import java.util.List;
import java.util.UUID;

@RestController
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService, RuleRepository ruleRepository) {
        this.recommendationService = recommendationService;

    }

    @GetMapping("/recommendation/{user_id}")
    public List<RecommendationObject> getRecommendation(@PathVariable("user_id") UUID id) {
        return recommendationService.getRecommendations(id);
    }



}
