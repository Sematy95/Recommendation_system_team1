package pro.sky.recommendation_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.recommendation_service.dto.RecommendationObject;
import pro.sky.recommendation_service.repository.RecommendationsRepository;

import java.util.UUID;

@RestController
public class RecommendationController {

    @GetMapping("/GET/recommendation/{user_id}")
    public RecommendationObject getRecommendation(@PathVariable("user_id") long id) {
        return new RecommendationObject("1","Тест","Тест контроллера");
    }

}
