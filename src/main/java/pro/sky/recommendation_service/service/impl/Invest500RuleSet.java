package pro.sky.recommendation_service.service.impl;

import org.springframework.stereotype.Component;
import pro.sky.recommendation_service.dto.RecommendationObject;
import pro.sky.recommendation_service.domain.Transaction;
import pro.sky.recommendation_service.repository.RecommendationsRepository;
import pro.sky.recommendation_service.service.RecommendationRuleSet;

import java.util.*;

import static pro.sky.recommendation_service.domain.enums.BaseProducts.*;
import static pro.sky.recommendation_service.domain.enums.ProductType.*;
import static pro.sky.recommendation_service.domain.enums.TransactionName.DEPOSIT;

@Component("Invest500RuleSet")
public class Invest500RuleSet implements RecommendationRuleSet {

    private final RecommendationObject recommendationObject = new RecommendationObject(
            UUID.fromString(INVEST500_UUID.getValue()),
            INVEST500_NAME.getValue(),
            INVEST500_TEXT.getValue()
    );

    private final RecommendationsRepository recommendationsRepository;

    public Invest500RuleSet(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public Optional<RecommendationObject> getRecommendationObject(UUID userId) {
        List<Transaction> transactions = recommendationsRepository.getTransactions(userId);

        boolean containDebit = false;
        int savingSum = 0;

        for (Transaction transaction : transactions) {
            if (transaction.getProductType().equals(DEBIT.getValue())) {
                containDebit = true;
            }

            if (transaction.getProductType().equals(INVEST.getValue())) {
                return Optional.empty();
            }

            if (transaction.getProductType().equals(SAVING) &&
                    transaction.getTransactionType().equals(DEPOSIT)) {
                savingSum += transaction.getAmount();
            }

        }

        if (containDebit && savingSum >= 1_000) {
            return Optional.of(recommendationObject);
        } else {
            return Optional.empty();
        }
    }
}
