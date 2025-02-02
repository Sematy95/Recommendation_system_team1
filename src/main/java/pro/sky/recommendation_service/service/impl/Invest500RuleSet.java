package pro.sky.recommendation_service.service.impl;

import org.springframework.stereotype.Component;
import pro.sky.recommendation_service.dto.RecommendationObject;
import pro.sky.recommendation_service.domain.Transaction;
import pro.sky.recommendation_service.repository.RecommendationsRepository;
import pro.sky.recommendation_service.service.RecommendationRuleSet;

import java.util.*;

/*
 * This class Invest500RuleSet implements the RecommendationRuleSet interface.
 * This class is responsible for generating investment recommendation for users based on their transaction history.
 * It specifically targets users who have:
 * 1. A DEBIT account.
 * 2. A SAVING account with a balance of at least 1000.
 */
@Component("Invest500RuleSet")
public class Invest500RuleSet implements RecommendationRuleSet {
    // Recommendation message
    // todo move to enumeration of recommendations
    private final RecommendationObject recommendationObject = new RecommendationObject(
            UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),
            "Invest 500",
            "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! " +
                    "Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца " +
                    "года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите " +
                    "возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными " +
                    "тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!"
    );

    private final RecommendationsRepository recommendationsRepository;

    public Invest500RuleSet(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    // Getting recommendation for a specific user
    @Override
    public Optional<RecommendationObject> getRecommendationObject(UUID userId) {
        // Get the transactional list of user from the repository
        List<Transaction> transactions = recommendationsRepository.getTransactions(userId);

        // Iterate through transactional list by product type such as DEBIT, INVEST, SAVING (DEPOSIT)
        boolean containDebit = false;
        int savingSum = 0;

        for (Transaction transaction : transactions) {
            // Check that the product type is "DEBIT"
            if (transaction.getProductType().equals("DEBIT")) {
                containDebit = true;
            }

            // Check that the product type is "INVEST"
            if (transaction.getProductType().equals("INVEST")) {
                return Optional.empty();
            }

            // Check that the product type is "SAVING" and the transaction type is "DEPOSIT"
            if (transaction.getProductType().equals("SAVING") &&
                    transaction.getTransactionType().equals("DEPOSIT")) {
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
