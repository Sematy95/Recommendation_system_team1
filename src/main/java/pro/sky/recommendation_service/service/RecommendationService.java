package pro.sky.recommendation_service.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pro.sky.recommendation_service.domain.Condition;
import pro.sky.recommendation_service.domain.enums.QueryType;
import pro.sky.recommendation_service.dto.RecommendationObject;
import pro.sky.recommendation_service.repository.ConditionsRepository;
import pro.sky.recommendation_service.domain.*;

import java.util.*;
import java.util.stream.Collectors;

import static pro.sky.recommendation_service.domain.enums.QueryType.*;


@Service
public class RecommendationService {


    private final DynamicRuleService dynamicRuleService;
    private final ConditionsRepository conditionsRepository;


    private final Map<String, RecommendationRuleSet> recommendationRuleSets;

    public RecommendationService( DynamicRuleService dynamicRuleService, ConditionsRepository conditionsRepository, Map<String, RecommendationRuleSet> recommendationRuleSets) {

        this.dynamicRuleService = dynamicRuleService;
        this.conditionsRepository = conditionsRepository;
        this.recommendationRuleSets = recommendationRuleSets;
    }


    @Cacheable(value = "recommendationCache")
    public List<RecommendationObject> getRecommendations(UUID user_id) {

//        List<RecommendationObject> recommendationObjects = new ArrayList<>();
//        for (RecommendationRuleSet recommendationRuleSet : recommendationRuleSets.values()) {
//            if (recommendationRuleSet.getRecommendationObject(user_id).isPresent()) {
//                recommendationObjects.add(recommendationRuleSet.getRecommendationObject(user_id).get());
//            }
//        }
//
//        return recommendationObjects;

        return recommendationRuleSets.values().stream()
                .filter(ruleSet -> ruleSet.getRecommendationObject(user_id).isPresent())
                .map(ruleSet -> ruleSet.getRecommendationObject(user_id).get())
                .collect(Collectors.toList());
    }

    private String CreateSqlFromCondition(Condition condition) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE WHEN ").append(condition.isNegate() ? "NOT " : "").append("EXISTS (");
        switch (condition.getQuery()) {
            case USER_OF,ACTIVE_USER_OF -> sql.append("SELECT * FROM CTE WHERE PRODUCT_TYPE = '")
                    .append(condition.getProductType())
                    .append("' LIMIT 1");
            case TRANSACTION_SUM_COMPARE ->
                    sql.append("SELECT SUM(TRANSACTION_AMOUNT) savings FROM CTE WHERE PRODUCT_TYPE = '")
                            .append(condition.getProductType())
                            .append("' AND TRANSACTION_TYPE = '")
                            .append(condition.getTransactionName())
                            .append(condition.getCompareType())
                            .append(condition.getCompareValue());
            case TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW ->
                    sql.append("SELECT * FROM ( (SELECT SUM(TRANSACTION_AMOUNT) deposits FROM CTE WHERE PRODUCT_TYPE = '")
                            .append(condition.getProductType())
                            .append("' AND TRANSACTION_TYPE = 'DEPOSIT' ) JOIN (")
                            .append("SELECT SUM(TRANSACTION_AMOUNT) withdraws FROM CTE WHERE PRODUCT_TYPE ='")
                            .append(condition.getProductType())
                            .append("' AND TRANSACTION_TYPE = 'WITHDRAW' ) )")
                            .append("WHERE deposits ")
                            .append(condition.getTransactionName())
                            .append(" withdraws");
        }
        sql.append(") THEN ").append(condition.getId()).append(" END ");
        return sql.toString();



}
}
