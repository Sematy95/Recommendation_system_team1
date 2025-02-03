package pro.sky.recommendation_service.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pro.sky.recommendation_service.domain.Condition;
import pro.sky.recommendation_service.domain.enums.*;
import pro.sky.recommendation_service.domain.*;
import pro.sky.recommendation_service.repository.ConditionsRepository;
import pro.sky.recommendation_service.repository.DynamicRuleRepository;
import pro.sky.recommendation_service.repository.RecommendationsRepository;
import pro.sky.recommendation_service.repository.StatisticRepository;
import pro.sky.recommendation_service.service.RecommendationService;

import java.util.*;

import static pro.sky.recommendation_service.domain.enums.TransactionName.DEPOSIT;
import static pro.sky.recommendation_service.domain.enums.TransactionName.WITHDRAW;


@Service
public class RecommendationServiceImpl implements RecommendationService {


    private static final Logger log = LoggerFactory.getLogger(RecommendationServiceImpl.class);


    private final RecommendationsRepository recommendationsRepository;
    private final DynamicRuleRepository dynamicRuleRepository;
    private final ConditionsRepository conditionsRepository;
    private final StatisticRepository statisticRepository;

    public RecommendationServiceImpl(RecommendationsRepository recommendationsRepository,
                                     DynamicRuleRepository dynamicRuleRepository,
                                     ConditionsRepository conditionsRepository,
                                     StatisticRepository statisticRepository) {
        this.recommendationsRepository = recommendationsRepository;
        this.dynamicRuleRepository = dynamicRuleRepository;
        this.conditionsRepository = conditionsRepository;
        this.statisticRepository = statisticRepository;
    }


    @Cacheable(value = "recommendationCache")
    @Override
    public ResponseForUser getRecommendations(UUID user_id) {
        log.info("Was invoked method for getting recommendations by user_id: {}", user_id);
        Collection<DynamicRule> validDynamicRules = new ArrayList<>();
        Collection<DynamicRule> dynamicRules = dynamicRuleRepository.findAll();
        dynamicRules.stream()
                .filter(dynamicRule -> checkDynamicRule(dynamicRule, user_id))
                .peek(dynamicRule -> statisticRepository.incrCount(dynamicRule))
                .forEach(validDynamicRules::add);
        return new ResponseForUser(user_id, validDynamicRules);
    }


    private Boolean checkDynamicRule(DynamicRule dynamicRule, UUID user_id) {
        if (dynamicRule == null) {
            log.info("dynamicRule is null");
            return false;
        }
        for (Condition condition : dynamicRule.getConditions()) {
            boolean check = checkAllQueryTypes(user_id, condition);
            if (!check) return false;
        }
        return true;
    }

    private Boolean checkAllQueryTypes(UUID user_ID, Condition condition) {

        boolean checkCondition = switchQuery(user_ID, condition);
        boolean checkParallelCondition = false;
        if (!Objects.isNull(condition.getParallelConditionId())) {
            Condition parallelCondition = conditionsRepository.findById(condition.getParallelConditionId()).orElse(null);
            checkParallelCondition = switchQuery(user_ID, parallelCondition);

        }
        return (checkCondition || checkParallelCondition);
    }

    private boolean switchQuery(UUID user_ID, Condition condition) {
        boolean checkCondition;
        switch (condition.getQuery()) {
            case USER_OF -> checkCondition = userOfCheck(user_ID, condition);
            case ACTIVE_USER_OF -> checkCondition = activeUserOfCheck(user_ID, condition);
            case TRANSACTION_SUM_COMPARE -> checkCondition = transactionSumCompareCheck(user_ID, condition);
            case TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW ->
                    checkCondition = transactionSumCompareDepositWithdrawCheck(user_ID, condition);
            default -> throw new IllegalArgumentException("Unexpected value: " + condition.getQuery());
        }
        return checkCondition;
    }

    private int productTypeCounter(UUID user_ID, ProductType productType) {
        List<Transaction> transactions = recommendationsRepository.getTransactions(user_ID);
        int counter = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getProductType().equals(productType.toString())) {
                counter++;
            }
        }
        return counter;
    }

    private boolean userOfCheck(UUID user_ID, Condition condition) {
        int amount = productTypeCounter(user_ID, condition.getProductType());
        boolean result = amount > 0;
        return condition.isNegate() ^ result;
    }

    private boolean activeUserOfCheck(UUID user_ID, Condition condition) {
        int amount = productTypeCounter(user_ID, condition.getProductType());
        boolean result = amount > 5;
        return condition.isNegate() ^ result;
    }

    private boolean transactionSumCompareCheck(UUID user_ID, Condition condition) {
        List<Transaction> transactions = recommendationsRepository.getTransactions(user_ID);
        int amount = 0;

        for (Transaction transaction : transactions) {
            if (transaction.getProductType().equals(condition.getProductType().toString())
                    && transaction.getTransactionType().equals(condition.getTransactionName().toString())) {
                amount += transaction.getAmount();
            }
        }
        boolean result = switchCompareType(amount, condition.getCompareValue(), condition);
        return condition.isNegate() ^ result;
    }

    private boolean transactionSumCompareDepositWithdrawCheck(UUID user_ID, Condition condition) {
        List<Transaction> transactions = recommendationsRepository.getTransactions(user_ID);
        int depositAmount = 0;
        int withdrawAmount = 0;

        for (Transaction transaction : transactions) {
            if (transaction.getProductType().equals(condition.getProductType().toString()) && transaction.getTransactionType().equals(DEPOSIT.getValue())) {
                depositAmount += transaction.getAmount();
            }
            if (transaction.getProductType().equals(condition.getProductType()) && transaction.getTransactionType().equals(WITHDRAW.getValue())) {
                withdrawAmount += transaction.getAmount();
            }
        }
        boolean result = switchCompareType(depositAmount, withdrawAmount, condition);
        return condition.isNegate() ^ result;
    }

    private boolean switchCompareType(int a, int b, Condition condition) {
        boolean result;
        switch (condition.getCompareType()) {
            case BIGGER -> result = a > b;
            case SMALLER -> result = a < b;
            case EQUAL -> result = a == b;
            case BIGGER_OR_EQUAL -> result = a >= b;
            case SMALLER_OR_EQUAL -> result = a <= b;
            default -> throw new IllegalArgumentException("Unexpected value: " + condition.getCompareType());
        }
        return result;
    }

    @Override
    public List<Transaction> getTransaction(UUID user_id) {
        return recommendationsRepository.getTransactions(user_id);
    }
}
