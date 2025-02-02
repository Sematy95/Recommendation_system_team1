package pro.sky.recommendation_service.service.impl;

import org.springframework.stereotype.Component;
import pro.sky.recommendation_service.dto.RecommendationObject;
import pro.sky.recommendation_service.domain.Transaction;
import pro.sky.recommendation_service.repository.RecommendationsRepository;
import pro.sky.recommendation_service.service.RecommendationRuleSet;

import java.util.*;

/*
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
    // Recommendation message
    // todo move to enumeration of recommendations
    private final RecommendationObject recommendationObject = new RecommendationObject(
            UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"),
            "Top Saving",
            "Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно" +
                    " накапливать деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем!" +
                    "Преимущества «Копилки»: Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет." +
                    "Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости." +
                    "Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг." +
                    "Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!"
    );

    private final RecommendationsRepository recommendationsRepository;

    public TopSavingRuleSet(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    // Getting recommendation for a specific user
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
            if (transaction.getProductType().equals("DEBIT")) {
                containDebit = true;
            }

            // Check that the product type is "SAVING" and the transaction type is "DEPOSIT"
            if (transaction.getProductType().equals("SAVING") &&
                    transaction.getTransactionType().equals("DEPOSIT")) {
                // Increase the counted number of SAVING-DEPOSIT transactions
                savingSum += transaction.getAmount();
            }

            // Check that the product type is "DEBIT" and the transaction type is "DEPOSIT"
            if (transaction.getProductType().equals("DEBIT") &&
                    transaction.getTransactionType().equals("DEPOSIT")) {
                // Increase the counted number of DEBIT-DEPOSIT transactions
                debitSum += transaction.getAmount();
            }

            // Check that the product type is "DEBIT" and the transaction type is "WITHDRAW"
            if (transaction.getProductType().equals("DEBIT") &&
                    transaction.getTransactionType().equals("WITHDRAW")) {
                // Increase the counted number of DEBIT-WITHDRAW transactions
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
