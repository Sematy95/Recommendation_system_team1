package pro.sky.recommendation_service.service;

import org.springframework.cache.annotation.Cacheable;
import pro.sky.recommendation_service.domain.ResponseForUser;
import pro.sky.recommendation_service.domain.Transaction;

import java.util.List;
import java.util.UUID;

public interface RecommendationService {
    @Cacheable(value = "recommendationCache")
    ResponseForUser getRecommendations(UUID user_id);

    List<Transaction> getTransaction(UUID user_id);
}
