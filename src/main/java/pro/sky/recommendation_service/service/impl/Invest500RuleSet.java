package pro.sky.recommendation_service.service.impl;

import org.springframework.stereotype.Component;
import pro.sky.recommendation_service.dto.RecommendationObject;
import pro.sky.recommendation_service.domain.Transaction;
import pro.sky.recommendation_service.repository.RecommendationsRepository;
import pro.sky.recommendation_service.service.RecommendationRuleSet;

import java.util.*;

@Component("Invest500RuleSet")
public class Invest500RuleSet implements RecommendationRuleSet {

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

    @Override
    public Optional<RecommendationObject> getRecommendationObject(UUID userId) {
        List<Transaction> transactions = recommendationsRepository.getTransactions(userId);

        boolean containDebit = false;
        int savingSum = 0;

        for (Transaction transaction : transactions) {
            if (transaction.getProductType().equals("DEBIT")) {
                containDebit = true;
            }

            if (transaction.getProductType().equals("INVEST")) {
                return Optional.empty();
            }

            if (transaction.getProductType().equals("SAVING") &&
                    transaction.getTransactionType().equals("DEPOSIT")) {
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
