package pro.sky.recommendation_service.service.impl;

import org.springframework.stereotype.Component;
import pro.sky.recommendation_service.dto.RecommendationObject;
import pro.sky.recommendation_service.domain.Transaction;
import pro.sky.recommendation_service.repository.RecommendationsRepository;
import pro.sky.recommendation_service.service.RecommendationRuleSet;

import java.util.*;

import static pro.sky.recommendation_service.domain.enums.BaseProducts.*;
import static pro.sky.recommendation_service.domain.enums.ProductType.CREDIT;
import static pro.sky.recommendation_service.domain.enums.ProductType.DEBIT;
import static pro.sky.recommendation_service.domain.enums.TransactionName.DEPOSIT;
import static pro.sky.recommendation_service.domain.enums.TransactionName.WITHDRAW;

@Component("SimpleLoanRuleSet")
public class SimpleLoanRuleSet implements RecommendationRuleSet {

    private final RecommendationObject recommendationObject = new RecommendationObject(
            UUID.fromString(SIMPLE_LOAN_UUID.getValue()),
            SIMPLE_LOAN_NAME.getValue(),
            SIMPLE_LOAN_TEXT.getValue()
    );

    private final RecommendationsRepository recommendationsRepository;

    public SimpleLoanRuleSet(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public Optional<RecommendationObject> getRecommendationObject(UUID userId) {
        List<Transaction> transactions = recommendationsRepository.getTransactions(userId);

        int debitSpend = 0;
        int debitSum = 0;

        for (Transaction transaction : transactions) {
            if (transaction.getProductType().equals(CREDIT.getValue())) {
                return Optional.empty();
            }

            if (transaction.getProductType().equals(DEBIT.getValue()) &&
                    transaction.getTransactionType().equals(DEPOSIT.getValue())) {
                debitSum += transaction.getAmount();
            }

            if (transaction.getProductType().equals(DEBIT.getValue()) &&
                    transaction.getTransactionType().equals(WITHDRAW.getValue())) {
                debitSpend += transaction.getAmount();
            }
        }

        if (debitSum > debitSpend && debitSpend >= 100_000) {
            return Optional.of(recommendationObject);
        } else {
            return Optional.empty();
        }
    }
}
