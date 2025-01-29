package pro.sky.recommendation_service.service.impl;

import org.springframework.stereotype.Component;
import pro.sky.recommendation_service.dto.RecommendationObject;
import pro.sky.recommendation_service.domain.Transaction;
import pro.sky.recommendation_service.repository.RecommendationsRepository;
import pro.sky.recommendation_service.service.RecommendationRuleSet;

import java.util.*;

/*
 * This class SimpleLoanRuleSet implements the RecommendationRuleSet interface.
 * This class is responsible for generating loan recommendation for users based on their transaction history.
 * It specifically targets users who have:
 * 1. A positive ratio between of DEPOSIT and WITHDRAW.
 * 2. The amount of WITHDRAW to the DEBIT account is at least 100_000.
 */
@Component("SimpleLoanRuleSet")
public class SimpleLoanRuleSet implements RecommendationRuleSet {

    // Recommendation message
    // todo move to enumeration of recommendations
    private final RecommendationObject recommendationObject = new RecommendationObject(
            UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"),
            "Простой кредит",
            "Откройте мир выгодных кредитов с нами!" +
                    "Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно!" +
                    " Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту. Почему выбирают нас:" +
                    "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов." +
                    "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении." +
                    "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое." +
                    "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!"
    );

    private final RecommendationsRepository recommendationsRepository;

    public SimpleLoanRuleSet(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    // Getting recommendation for a specific user
    @Override
    public Optional<RecommendationObject> getRecommendationObject(UUID userId) {
        // Get the transactional list of user from the repository
        List<Transaction> transactions = recommendationsRepository.getTransactions(userId);

        // Iterate through transactional list by product type such as CREDIT, DEBIT (DEPOSIT,WITHDRAW)
        // todo refactor it, because these values are initially empty
        int debitSpend = 0;
        int debitSum = 0;
        for (Transaction transaction : transactions) {
            // Check that the product type is "CREDIT"
            if (transaction.getProductType().equals("CREDIT")) {
                return Optional.empty();
            }

            // Check that the product type is "DEBIT" and the transaction type is "DEPOSIT"
            if (transaction.getProductType().equals("DEBIT") && transaction.getTransactionType().equals("DEPOSIT")) {
                // Increase the counted number of DEPOSIT transactions
                debitSum += transaction.getAmount();
            }

            // Check that the product type is "DEBIT" and the transaction type is "WITHDRAW"
            if (transaction.getProductType().equals("DEBIT") && transaction.getTransactionType().equals("WITHDRAW")) {
                // Increase the counted number of WITHDRAW transactions
                // todo rename. Withdraw cash is spend, but withdraw is transfer money to another account
                debitSpend += transaction.getAmount();
            }
        }

        /*
         * Check that the user has:
         * 1. A positive ratio between of DEPOSIT and WITHDRAW.
         * 2. The amount of WITHDRAW to the DEBIT account is at least 100_000.
         */
        if (debitSum > debitSpend && debitSpend >= 100_000) {
            return Optional.of(recommendationObject);
        } else {
            return Optional.empty();
        }
    }
}
