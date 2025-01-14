package pro.sky.recommendation_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.recommendation_service.dto.RecommendationObject;
import pro.sky.recommendation_service.repository.RecommendationsRepository;

import java.util.UUID;

@RestController
public class RecommendationController {

    private final RecommendationsRepository recommendationsRepository;

    public RecommendationController(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @GetMapping("/GET/recommendation/{user_id}")
    public RecommendationObject getRecommendation(@PathVariable("user_id") long id) {
        return new RecommendationObject(id, "Тест", "Тест контроллера");
    }

    @GetMapping("/GET/bd_test/{user_id}")
    public Integer test_bd(@PathVariable("user_id") UUID id) {
        return recommendationsRepository.getRandomTransactionAmount(id);

    }
}
