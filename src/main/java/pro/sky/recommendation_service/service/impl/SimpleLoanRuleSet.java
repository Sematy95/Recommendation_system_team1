package pro.sky.recommendation_service.service.impl;

import org.springframework.stereotype.Component;
import pro.sky.recommendation_service.domain.Transaction;
import pro.sky.recommendation_service.dto.RecommendationObject;
import pro.sky.recommendation_service.repository.RecommendationsRepository;
import pro.sky.recommendation_service.service.RecommendationRuleSet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static pro.sky.recommendation_service.domain.enums.BaseProducts.*;
import static pro.sky.recommendation_service.domain.enums.ProductType.CREDIT;
import static pro.sky.recommendation_service.domain.enums.ProductType.DEBIT;
import static pro.sky.recommendation_service.domain.enums.TransactionName.DEPOSIT;
import static pro.sky.recommendation_service.domain.enums.TransactionName.WITHDRAW;

/**
 * This class SimpleLoanRuleSet implements the RecommendationRuleSet interface.
 * This class is responsible for generating loan recommendation for users based on their transaction history.
 * It specifically targets users who have:
 * 1. A positive ratio between of DEPOSIT and WITHDRAW.
 * 2. The amount of WITHDRAW to the DEBIT account is at least 100_000.
 */
@Component("SimpleLoanRuleSet")
public class SimpleLoanRuleSet implements RecommendationRuleSet {
    private final RecommendationObject recommendationObject = new RecommendationObject(UUID.fromString(SIMPLE_LOAN_UUID.getValue()), SIMPLE_LOAN_NAME.getValue(), SIMPLE_LOAN_TEXT.getValue());

    private final RecommendationsRepository recommendationsRepository;

    public SimpleLoanRuleSet(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    // Methodology for determining user recommendations
    @Override
    public Optional<RecommendationObject> getRecommendationObject(UUID userId) {
        // Get the transactional list of user from the repository
        List<Transaction> transactions = recommendationsRepository.getTransactions(userId);

        // Iterate through transactional list by product type such as CREDIT, DEBIT (DEPOSIT,WITHDRAW)
        int debitSpend = 0;
        int debitSum = 0;
        for (Transaction transaction : transactions) {
            // Check that the product type is "CREDIT"
            if (transaction.getProductType().equals(CREDIT.getValue())) {
                return Optional.empty();
            }

            // Check that the product type is "DEBIT" and the transaction type is "DEPOSIT"
            if (transaction.getProductType().equals(DEBIT.getValue()) &&
                    transaction.getTransactionType().equals(DEPOSIT.getValue())) {
                debitSum += transaction.getAmount();
            }

            // Check that the product type is "DEBIT" and the transaction type is "WITHDRAW"
            if (transaction.getProductType().equals(DEBIT.getValue()) &&
                    transaction.getTransactionType().equals(WITHDRAW.getValue())) {
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
