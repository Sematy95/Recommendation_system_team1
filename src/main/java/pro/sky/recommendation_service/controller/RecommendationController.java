package pro.sky.recommendation_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.recommendation_service.domain.Rule;
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

    @PostMapping("/rule")
    public void addRule(@RequestParam("product_name") String productName,
                        @RequestParam("product_id") UUID productId,
                        @RequestParam("product_text") String productText,
                        @RequestParam("rule") Rule rule) {}

    @DeleteMapping("/rule/{rule_id}")
    public void deleteRule(@PathVariable("rule_id") UUID id) {}

    @GetMapping("/rule")
    public void getAllRules() {}
}
