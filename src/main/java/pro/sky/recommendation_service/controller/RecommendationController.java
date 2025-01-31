package pro.sky.recommendation_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import pro.sky.recommendation_service.domain.ResponseForUser;
import pro.sky.recommendation_service.domain.Transaction;
import pro.sky.recommendation_service.repository.RuleRepository;
import pro.sky.recommendation_service.service.RecommendationService;
import pro.sky.recommendation_service.service.impl.RecommendationServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService, RuleRepository ruleRepository) {
        this.recommendationService = recommendationService;

    }
    @Operation(summary = "list of all valid recommendations for user")
    @GetMapping("/recommendation/{user_id}")
    public ResponseForUser getRecommendation(@PathVariable("user_id") UUID id) {
        return recommendationService.getRecommendations(id);
    }

    @Operation(summary = "list of all user's transactions")
    @GetMapping("/transaction/{user_id}")
    public List<Transaction> getTransaction(@PathVariable("user_id") UUID id) {
        return recommendationService.getTransaction(id);
    }



}
