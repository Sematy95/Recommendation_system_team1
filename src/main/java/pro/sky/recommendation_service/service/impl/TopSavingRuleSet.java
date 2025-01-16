package pro.sky.recommendation_service.service.impl;

import org.springframework.stereotype.Component;
import pro.sky.recommendation_service.dto.RecommendationObject;
import pro.sky.recommendation_service.domain.Transaction;
import pro.sky.recommendation_service.repository.RecommendationsRepository;
import pro.sky.recommendation_service.service.RecommendationRuleSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component("TopSavingRuleSet")
public class TopSavingRuleSet implements RecommendationRuleSet {

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

    @Override
    public Optional<RecommendationObject> getRecommendationObject(UUID userId) {
        // TODO transactions query

        List<Transaction> transactions = new ArrayList<>();
        boolean containDebit = false;
        int debitSpend = 0;
        int debitSum = 0;
        int savingSum = 0;

        for (Transaction transaction : transactions) {
            if (transaction.getProductType().equals("DEBIT")) {
                containDebit = true;
            }

            if (transaction.getProductType().equals("SAVING") &&
                    transaction.getTransactionType().equals("DEPOSIT")) {
                savingSum += transaction.getAmount();
            }

            if (transaction.getProductType().equals("DEBIT") &&
                    transaction.getTransactionType().equals("DEPOSIT")) {
                debitSum += transaction.getAmount();
            }

            if (transaction.getProductType().equals("DEBIT") &&
                    transaction.getTransactionType().equals("WITHDRAW")) {
                debitSpend += transaction.getAmount();
            }
        }

        if (containDebit == true && (savingSum >= 50_000 || debitSum >= 50_000) && debitSum > debitSpend) {
            return Optional.of(recommendationObject);
        } else {
            return Optional.empty();
        }
    }
}
