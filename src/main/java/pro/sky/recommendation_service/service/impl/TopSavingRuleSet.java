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
import static pro.sky.recommendation_service.domain.enums.ProductType.DEBIT;
import static pro.sky.recommendation_service.domain.enums.ProductType.SAVING;
import static pro.sky.recommendation_service.domain.enums.TransactionName.DEPOSIT;
import static pro.sky.recommendation_service.domain.enums.TransactionName.WITHDRAW;

/**
 * This class TopSavingRuleSet implements the RecommendationRuleSet interface.
 * This class is responsible for generating investment recommendation for users based on their transaction history.
 * It specifically targets users who have:
 * 1. A DEBIT account.
 * 2. The amount of DEPOSIT to the DEBIT account is at least 50_000.
 * 3. The amount of WITHDRAW to the DEBIT account is at least 50_000.
 * 4. A greater value between of DEBIT-DEPOSIT and DEBIT-WITHDRAW.
 */
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

    // Methodology for determining user recommendations
    @Override
    public Optional<RecommendationObject> getRecommendationObject(UUID userId) {
        // Get the transactional list of user from the repository
        List<Transaction> transactions = recommendationsRepository.getTransactions(userId);

        // Iterate through transactional list by product type such as DEBIT, SAVING, SAVING (DEPOSIT) and transaction type such as DEPOSIT, WITHDRAW
        boolean containDebit = false;
        int debitSpend = 0;
        int debitSum = 0;
        int savingSum = 0;
        for (Transaction transaction : transactions) {
            // Check that the product type is "DEBIT"
            if (transaction.getProductType().equals(DEBIT.getValue())) {
                containDebit = true;
            }

            // Check that the product type is "SAVING" and the transaction type is "DEPOSIT"
            if (transaction.getProductType().equals(SAVING.getValue()) &&
                    transaction.getTransactionType().equals(DEPOSIT.getValue())) {
                savingSum += transaction.getAmount();
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
         * 1. A DEBIT account.
         * 2. The amount of DEPOSIT to the DEBIT account is at least 50_000.
         * 3. The amount of WITHDRAW to the DEBIT account is at least 50_000.
         * 4. A greater value between of DEBIT-DEPOSIT and DEBIT-WITHDRAW.
         */
        if (containDebit && (savingSum >= 50_000 || debitSum >= 50_000) && debitSum > debitSpend) {
            return Optional.of(recommendationObject);
        } else {
            return Optional.empty();
        }
    }
}
