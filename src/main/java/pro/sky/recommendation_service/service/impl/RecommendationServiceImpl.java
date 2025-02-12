package pro.sky.recommendation_service.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pro.sky.recommendation_service.domain.*;
import pro.sky.recommendation_service.domain.enums.ProductType;
import pro.sky.recommendation_service.repository.ConditionsRepository;
import pro.sky.recommendation_service.repository.DynamicRuleRepository;
import pro.sky.recommendation_service.repository.RecommendationsRepository;
import pro.sky.recommendation_service.repository.StatisticRepository;
import pro.sky.recommendation_service.service.RecommendationService;

import java.util.*;
import java.util.stream.Collectors;

import static pro.sky.recommendation_service.domain.enums.TransactionName.DEPOSIT;
import static pro.sky.recommendation_service.domain.enums.TransactionName.WITHDRAW;

/**
 * Service class implementing the RecommendationService interface.
 * This class provides methods for retrieving recommendations for users,
 * both by user ID and username.  It also includes caching to improve performance.
 */
@Service
public class RecommendationServiceImpl implements RecommendationService {
    private static final Logger log = LoggerFactory.getLogger(RecommendationServiceImpl.class);

    private final RecommendationsRepository recommendationsRepository;
    private final DynamicRuleRepository dynamicRuleRepository;
    private final ConditionsRepository conditionsRepository;
    private final StatisticRepository statisticRepository;

    public RecommendationServiceImpl(RecommendationsRepository recommendationsRepository, DynamicRuleRepository dynamicRuleRepository, ConditionsRepository conditionsRepository, StatisticRepository statisticRepository) {
        this.recommendationsRepository = recommendationsRepository;
        this.dynamicRuleRepository = dynamicRuleRepository;
        this.conditionsRepository = conditionsRepository;
        this.statisticRepository = statisticRepository;
    }

    /**
     * Метод для вывода всех рекомендаций, актуальных для пользователя
     *
     * @param user_id Идентификатор пользователя
     * @return Список всех рекомендаций, актуальных для данного пользователя
     */
    @Cacheable(value = "recommendationCache")
    @Override
    public ResponseForUser getRecommendations(UUID user_id) {
        log.info("Was invoked method for getting recommendations by user_id: {}", user_id);
        Collection<DynamicRule> validDynamicRules = new ArrayList<>();
        Collection<DynamicRule> dynamicRules = dynamicRuleRepository.findAll();
        dynamicRules.stream().filter(dynamicRule -> checkDynamicRule(dynamicRule, user_id)).forEach(validDynamicRules::add);

        Collection<Statistic> statistics = statisticRepository.findAll();
        statistics.stream().filter(statistic -> validDynamicRules.contains(statistic.getDynamicRule())).peek(Statistic::incrCount).collect(Collectors.toList());
        statisticRepository.saveAll(statistics);

        return new ResponseForUser(user_id, validDynamicRules);
    }

    /**
     * Метод для вывода всех рекомендаций, актуальных для пользователя
     *
     * @param username Имя пользователя
     * @return Список всех рекомендаций, актуальных для данного пользователя
     */
    @Cacheable(value = "recommendationCache")
    @Override
    public ResponseForUser getRecommendationsByUsername(String username) {
        log.info("Was invoked method for getting recommendations by username: {}", username);
        UUID user_id = recommendationsRepository.getUserIdByUserName(username);
        return getRecommendations(user_id);
    }

    /**
     * Метод, проверяющий, актуально ли динамическое правило (рекомендация) для данного пользователя
     *
     * @param dynamicRule Динамическое правило
     * @param user_id     Идентификатор пользователя
     * @return true или false
     */
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

    /**
     * Метод, проверяющий актуальность условия (condition) из динамического правила для пользователя или параллельного условия (если есть)
     *
     * @param user_ID   Идентификатор пользователя
     * @param condition Условие из динамического правила
     * @return true или false
     */
    private Boolean checkAllQueryTypes(UUID user_ID, Condition condition) {

        boolean checkCondition = switchQuery(user_ID, condition);
        boolean checkParallelCondition = false;
        if (!Objects.isNull(condition.getParallelConditionId())) {
            Condition parallelCondition = conditionsRepository.findById(condition.getParallelConditionId()).orElse(null);
            checkParallelCondition = switchQuery(user_ID, parallelCondition);

        }
        return (checkCondition || checkParallelCondition);
    }

    /**
     * Метод для перебора всех возможных типов запроса из условия
     *
     * @param user_ID   Идентификатор пользователя
     * @param condition Условие из динамического правила
     * @return true или false
     */
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

    /**
     * Метод-счётчик для проверки количества транзакций с определенным продуктом у пользователя для проверки запросов USER_OF и ACTIVE_USER_OF
     *
     * @param user_ID     Идентификатор пользователя
     * @param productType Тип продукта в условии
     * @return число транзакций с определенным продуктом (int)
     */
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

    /**
     * Метод для проверки типа запроса USER_OF в условии
     *
     * @param user_ID   Идентификатор пользователя
     * @param condition Условие
     * @return true или false
     */
    private boolean userOfCheck(UUID user_ID, Condition condition) {
        int amount = productTypeCounter(user_ID, condition.getProductType());
        boolean result = amount > 0;
        return condition.isNegate() ^ result;
    }

    /**
     * Метод для проверки типа запроса ACTIVE_USER_OF в условии
     *
     * @param user_ID   Идентификатор пользователя
     * @param condition Условие
     * @return true или false
     */
    private boolean activeUserOfCheck(UUID user_ID, Condition condition) {
        int amount = productTypeCounter(user_ID, condition.getProductType());
        boolean result = amount > 5;
        return condition.isNegate() ^ result;
    }

    /**
     * Метод для проверки типа запроса TRANSACTION_SUM_COMPARE в условии
     *
     * @param user_ID   Идентификатор пользователя
     * @param condition Условие
     * @return true или false
     */
    private boolean transactionSumCompareCheck(UUID user_ID, Condition condition) {
        List<Transaction> transactions = recommendationsRepository.getTransactions(user_ID);
        int amount = 0;

        for (Transaction transaction : transactions) {
            if (transaction.getProductType().equals(condition.getProductType().toString()) && transaction.getTransactionType().equals(condition.getTransactionName().toString())) {
                amount += transaction.getAmount();
            }
        }
        boolean result = switchCompareType(amount, condition.getCompareValue(), condition);
        return condition.isNegate() ^ result;
    }

    /**
     * Метод для проверки типа запроса TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW в условии
     *
     * @param user_ID   Идентификатор пользователя
     * @param condition Условие
     * @return true или false
     */
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

    /**
     * Метод для перебора всех возможных операторов сравнения в запросах TRANSACTION_SUM_COMPARE и TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW
     *
     * @param a         Первое число
     * @param b         Второе число
     * @param condition Условие
     * @return результат сравнения (true или false)
     */
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

    /**
     * Метод для получения всех транзакций пользователя
     *
     * @param user_id Идентификатор пользователя
     * @return Список транзакций пользователя с типом продукта, видом транзакции (пополнение или трата) и суммой транзакции
     */
    @Override
    public List<Transaction> getTransaction(UUID user_id) {
        return recommendationsRepository.getTransactions(user_id);
    }
}
