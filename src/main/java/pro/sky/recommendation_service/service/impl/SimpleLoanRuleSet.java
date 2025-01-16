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

@Component("SimpleLoanRuleSet")
public class SimpleLoanRuleSet implements RecommendationRuleSet {

    private final RecommendationObject recommendationObject = new RecommendationObject(
            UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"),
            "Простой кредит",
            "Откройте мир выгодных кредитов с нами!" +
                    "Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно!" +
                    " Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту. Почему выбирают нас:" +
                    "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов." +
                    "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении." +
                    "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое." +
                    "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!"
    );

    private final RecommendationsRepository recommendationsRepository;

    public SimpleLoanRuleSet(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public Optional<RecommendationObject> getRecommendationObject(UUID userId) {
        // TODO transactions query

        List<Transaction> transactions = new ArrayList<>();
        int debitSpend = 0;
        int debitSum = 0;

        for (Transaction transaction : transactions) {
            if (transaction.getProductType().equals("CREDIT")) {
                return Optional.empty();
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

        if (debitSum > debitSpend && debitSpend >= 100_000) {
            return Optional.of(recommendationObject);
        } else {
            return Optional.empty();
        }
    }
}
