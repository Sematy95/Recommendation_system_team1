package pro.sky.recommendation_service.service.impl;

import org.springframework.stereotype.Component;
import pro.sky.recommendation_service.dto.RecommendationObject;
import pro.sky.recommendation_service.domain.Transaction;
import pro.sky.recommendation_service.repository.RecommendationsRepository;
import pro.sky.recommendation_service.service.RecommendationRuleSet;

import java.util.*;

import static pro.sky.recommendation_service.domain.enums.BaseProducts.*;
import static pro.sky.recommendation_service.domain.enums.ProductType.DEBIT;
import static pro.sky.recommendation_service.domain.enums.ProductType.SAVING;
import static pro.sky.recommendation_service.domain.enums.TransactionName.DEPOSIT;
import static pro.sky.recommendation_service.domain.enums.TransactionName.WITHDRAW;

@Component("TopSavingRuleSet")
public class TopSavingRuleSet implements RecommendationRuleSet {

    private final RecommendationObject recommendationObject = new RecommendationObject(
            UUID.fromString(TOP_SAVING_UUID.getValue()),
            TOP_SAVING_NAME.getValue(),
            TOP_SAVING_TEXT.getValue()
    );

    private final RecommendationsRepository recommendationsRepository;

    public TopSavingRuleSet(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public Optional<RecommendationObject> getRecommendationObject(UUID userId) {
        List<Transaction> transactions = recommendationsRepository.getTransactions(userId);

        boolean containDebit = false;
        int debitSpend = 0;
        int debitSum = 0;
        int savingSum = 0;

        for (Transaction transaction : transactions) {
            if (transaction.getProductType().equals(DEBIT.getValue())) {
                containDebit = true;
            }

            if (transaction.getProductType().equals(SAVING.getValue()) &&
                    transaction.getTransactionType().equals(DEPOSIT.getValue())) {
                savingSum += transaction.getAmount();
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

        if (containDebit && (savingSum >= 50_000 || debitSum >= 50_000) && debitSum > debitSpend) {
            return Optional.of(recommendationObject);
        } else {
            return Optional.empty();
        }
    }
}
