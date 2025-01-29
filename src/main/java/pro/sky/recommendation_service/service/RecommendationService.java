package pro.sky.recommendation_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import pro.sky.recommendation_service.exception.ConditionNotFoundException;
import pro.sky.recommendation_service.repository.ConditionsRepository;
import pro.sky.recommendation_service.domain.*;
import pro.sky.recommendation_service.repository.RecommendationsRepository;

import java.util.*;
import java.util.stream.Collectors;

import static pro.sky.recommendation_service.domain.enums.QueryType.*;


@Service
public class RecommendationService {


    private static final Logger log = LoggerFactory.getLogger(RecommendationService.class);
    private final DynamicRuleService dynamicRuleService;
    private final ConditionsRepository conditionsRepository;
    private final RecommendationsRepository recommendationsRepository;



    private final Map<String, RecommendationRuleSet> recommendationRuleSets;

    public RecommendationService(DynamicRuleService dynamicRuleService, ConditionsRepository conditionsRepository, RecommendationsRepository recommendationsRepository, Map<String, RecommendationRuleSet> recommendationRuleSets) {
        this.dynamicRuleService = dynamicRuleService;
        this.conditionsRepository = conditionsRepository;
        this.recommendationsRepository = recommendationsRepository;
        this.recommendationRuleSets = recommendationRuleSets;
    }



    @Cacheable(value = "recommendationCache")
    public ResponseForUser getRecommendations(UUID user_id) {
        Collection<Condition> conditions = conditionsRepository.findAll();
        recommendationsRepository.createTemporaryTransactionTableForUser(user_id);
        StringBuilder sql = new StringBuilder("WITH conditions_TT AS(SELECT C1 as TRUE_CONDITION FROM(");
        boolean isFirst = true;
        for (Condition condition : conditions) {
            if (!isFirst) {
                sql.append(" UNION ");
            } else {
                isFirst = false;
            }
            if (Objects.isNull(condition.getParallelConditionId())) {
                sql.append(createSqlFromCondition(condition));
            } else {
                long parallelConditionId = condition.getParallelConditionId();
                Condition parallelCondition = conditionsRepository.findById(parallelConditionId).orElseThrow(() -> new ConditionNotFoundException(parallelConditionId));
                sql.append("SELECT CASE WHEN EXISTS (");
                sql.append(createSqlFromCondition(condition));
                sql.append(" UNION ");
                sql.append(createSqlFromCondition(parallelCondition));
                sql.append(") THEN ").append(condition.getId()).append(" END ");

            }
        }
        sql.append(")) SELECT * FROM dynamic_rule as d WHERE d.ID NOT IN (SELECT DISTINCT dynamic_rule_id FROM dynamic_rule_condition LEFT JOIN conditions_TT ON rules_id=true_condition WHERE true_condition IS NULL);");
        log.info("resulting sql "+sql.toString());
        ResponseForUser responseForUser = new ResponseForUser();
        responseForUser.setUserId(user_id);
        responseForUser.setDynamicRules(recommendationsRepository.createDynamicRules(sql.toString()));
        return responseForUser;
//        List<RecommendationObject> recommendationObjects = new ArrayList<>();
//        for (RecommendationRuleSet recommendationRuleSet : recommendationRuleSets.values()) {
//            if (recommendationRuleSet.getRecommendationObject(user_id).isPresent()) {
//                recommendationObjects.add(recommendationRuleSet.getRecommendationObject(user_id).get());
//            }
//        }
//
//        return recommendationObjects;

//        return recommendationRuleSets.values().stream()
//                .filter(ruleSet -> ruleSet.getRecommendationObject(user_id).isPresent())
//                .map(ruleSet -> ruleSet.getRecommendationObject(user_id).get())
//                .collect(Collectors.toList());
    }

    private String createSqlFromCondition(Condition condition) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE WHEN ").append(condition.isNegate() ? "NOT " : "").append("EXISTS (");
        switch (condition.getQuery()) {
            case USER_OF, ACTIVE_USER_OF -> sql.append("SELECT * FROM TT WHERE PRODUCT_TYPE = '")
                    .append(condition.getProductType())
                    .append("' LIMIT 1");
            case TRANSACTION_SUM_COMPARE ->
                    sql.append("SELECT SUM(TRANSACTION_AMOUNT) savings FROM TT WHERE PRODUCT_TYPE = '")
                            .append(condition.getProductType())
                            .append("' AND TRANSACTION_TYPE = '")
                            .append(condition.getTransactionName())
                            .append(condition.getCompareType())
                            .append(condition.getCompareValue());
            case TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW ->
                    sql.append("SELECT * FROM ( (SELECT SUM(TRANSACTION_AMOUNT) deposits FROM TT WHERE PRODUCT_TYPE = '")
                            .append(condition.getProductType())
                            .append("' AND TRANSACTION_TYPE = 'DEPOSIT' ) JOIN (")
                            .append("SELECT SUM(TRANSACTION_AMOUNT) withdraws FROM TT WHERE PRODUCT_TYPE ='")
                            .append(condition.getProductType())
                            .append("' AND TRANSACTION_TYPE = 'WITHDRAW' ) )")
                            .append("WHERE deposits ")
                            .append(condition.getTransactionName())
                            .append(" withdraws");
        }
        sql.append(") THEN ").append(condition.getId()).append(" END ");
        log.info("sql from condition "+sql.toString());
        return sql.toString();


    }
    public List<Transaction> getTransaction(UUID user_id) {
        return recommendationsRepository.getTransactions(user_id);
    }
}
