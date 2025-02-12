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
import static pro.sky.recommendation_service.domain.enums.ProductType.*;
import static pro.sky.recommendation_service.domain.enums.TransactionName.DEPOSIT;

/**
 * This class Invest500RuleSet implements the RecommendationRuleSet interface.
 * This class is responsible for generating investment recommendation for users based on their transaction history.
 * It specifically targets users who have:
 * 1. A DEBIT account.
 * 2. A SAVING account with a balance of at least 1000.
 */
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

    // Methodology for determining user recommendations
    @Override
    public Optional<RecommendationObject> getRecommendationObject(UUID userId) {
        // Get the transactional list of user from the repository
        List<Transaction> transactions = recommendationsRepository.getTransactions(userId);

        // Iterate through transactional list by product type such as DEBIT, INVEST, SAVING (DEPOSIT)
        boolean containDebit = false;
        int savingSum = 0;
        for (Transaction transaction : transactions) {
            // Check that the product type is "DEBIT"
            if (transaction.getProductType().equals(DEBIT.getValue())) {
                containDebit = true;
            }

            // Check that the product type is "INVEST"
            if (transaction.getProductType().equals(INVEST.getValue())) {
                return Optional.empty();
            }

            // Check that the product type is "SAVING" and the transaction type is "DEPOSIT"
            if (transaction.getProductType().equals(SAVING) &&
                    transaction.getTransactionType().equals(DEPOSIT)) {
                // Increase the counted number of DEPOSIT transactions
                savingSum += transaction.getAmount();
            }
        }

        /*
         * Check that the user has:
         * 1. A DEBIT account.
         * 2. A SAVING account with a balance of at least 1000.
         */
        if (containDebit && savingSum >= 1_000) {
            return Optional.of(recommendationObject);
        } else {
            return Optional.empty();
        }
    }
}
