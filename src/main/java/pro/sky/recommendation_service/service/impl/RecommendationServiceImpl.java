package pro.sky.recommendation_service.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pro.sky.recommendation_service.domain.Condition;
import pro.sky.recommendation_service.domain.enums.*;
import pro.sky.recommendation_service.domain.*;
import pro.sky.recommendation_service.repository.DynamicRuleRepository;
import pro.sky.recommendation_service.repository.RecommendationsRepository;
import pro.sky.recommendation_service.service.RecommendationService;

import java.util.*;

import static pro.sky.recommendation_service.domain.enums.TransactionName.DEPOSIT;
import static pro.sky.recommendation_service.domain.enums.TransactionName.WITHDRAW;


@Service
public class RecommendationServiceImpl implements RecommendationService {


    private static final Logger log = LoggerFactory.getLogger(RecommendationServiceImpl.class);


    private final RecommendationsRepository recommendationsRepository;
    private final DynamicRuleRepository dynamicRuleRepository;

    public RecommendationServiceImpl(RecommendationsRepository recommendationsRepository, DynamicRuleRepository dynamicRuleRepository) {
        this.recommendationsRepository = recommendationsRepository;
        this.dynamicRuleRepository = dynamicRuleRepository;
    }

    @Cacheable(value = "recommendationCache")
    @Override
    public ResponseForUser getRecommendations(UUID user_id) {
        Collection<DynamicRule> validDynamicRules = new ArrayList<>();
        Collection<DynamicRule> dynamicRules = dynamicRuleRepository.findAll();
        dynamicRules.stream().filter(dynamicRule -> checkDynamicRule(dynamicRule, user_id)).forEach(validDynamicRules::add);
        ResponseForUser responseForUser = new ResponseForUser(user_id, validDynamicRules);
        return responseForUser;
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

        switch (condition.getQuery()) {
            case USER_OF:
                return userOfCheck(user_ID, condition);
            case ACTIVE_USER_OF:
                return activeUserOfCheck(user_ID, condition);
            case TRANSACTION_SUM_COMPARE:
                return transactionSumCompareCheck(user_ID, condition);
            case TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW:
                return transactionSumCompareDepositWithdraw(user_ID, condition);
            default:
                throw new IllegalArgumentException("Unexpected value: " + condition.getCompareType());
        }

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
        return !condition.isNegate() && result;
    }

    private boolean activeUserOfCheck(UUID user_ID, Condition condition) {
        int amount = productTypeCounter(user_ID, condition.getProductType());
        boolean result = amount > 5;
        return !condition.isNegate() && result;
    }

    private boolean transactionSumCompareCheck(UUID user_ID, Condition condition) {
        List<Transaction> transactions = recommendationsRepository.getTransactions(user_ID);
        int amount = 0;
        boolean result;
        for (Transaction transaction : transactions) {
            if (transaction.getProductType().equals(condition.getProductType().toString())
                    && transaction.getTransactionType().equals(condition.getTransactionName().toString())) {
                amount += transaction.getAmount();
            }
        }
        switch (condition.getCompareType()) {
            case BIGGER -> result = amount > condition.getCompareValue();
            case SMALLER -> result = amount < condition.getCompareValue();
            case EQUAL -> result = amount == condition.getCompareValue();
            case BIGGER_OR_EQUAL -> result = amount >= condition.getCompareValue();
            case SMALLER_OR_EQUAL -> result = amount <= condition.getCompareValue();
            default -> throw new IllegalArgumentException("Unexpected value: " + condition.getCompareType());
        }
        return !condition.isNegate() && result;
    }

    private boolean transactionSumCompareDepositWithdraw(UUID user_ID, Condition condition) {
        List<Transaction> transactions = recommendationsRepository.getTransactions(user_ID);
        int depositAmount = 0;
        int withdrawAmount = 0;
        boolean result;
        for (Transaction transaction : transactions) {
            if (transaction.getProductType().equals(condition.getProductType().toString()) && transaction.getTransactionType().equals(DEPOSIT.getValue())) {
                depositAmount += transaction.getAmount();
            }
            if (transaction.getProductType().equals(condition.getProductType()) && transaction.getTransactionType().equals(WITHDRAW.getValue())) {
                withdrawAmount += transaction.getAmount();
            }
        }
        switch (condition.getCompareType()) {
            case BIGGER -> result = depositAmount > withdrawAmount;
            case SMALLER -> result = depositAmount < withdrawAmount;
            case EQUAL -> result = depositAmount == withdrawAmount;
            case BIGGER_OR_EQUAL -> result = depositAmount >= withdrawAmount;
            case SMALLER_OR_EQUAL -> result = depositAmount <= withdrawAmount;
            default -> throw new IllegalArgumentException("Unexpected value: " + condition.getCompareType());
        }
        return !condition.isNegate() && result;
    }

    @Override
    public List<Transaction> getTransaction(UUID user_id) {
        return recommendationsRepository.getTransactions(user_id);
    }
}
