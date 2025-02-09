package pro.sky.recommendation_service.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.recommendation_service.domain.Condition;
import pro.sky.recommendation_service.domain.DynamicRule;
import pro.sky.recommendation_service.domain.Transaction;
import pro.sky.recommendation_service.domain.enums.ProductType;
import pro.sky.recommendation_service.domain.enums.QueryType;
import pro.sky.recommendation_service.repository.ConditionsRepository;
import pro.sky.recommendation_service.repository.DynamicRuleRepository;
import pro.sky.recommendation_service.repository.RecommendationsRepository;
import pro.sky.recommendation_service.repository.StatisticRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceImplTest {

    @Mock
    DynamicRuleRepository dynamicRuleRepository;

    @Mock
    RecommendationsRepository recommendationsRepository;

    @Mock
    ConditionsRepository conditionsRepository;

    @Mock
    StatisticRepository statisticRepository;

    @InjectMocks
    RecommendationServiceImpl recommendationServiceImpl;

    private final Condition condition = new Condition(QueryType.USER_OF, ProductType.CREDIT, null, null, null, false, null);
    private final Condition condition2 = new Condition(QueryType.USER_OF, ProductType.DEBIT, null, null, null, false, null);
    private final Condition condition3 = new Condition(QueryType.USER_OF, ProductType.INVEST, null, null, null, false, null);

    List<Condition> conditions1 = List.of(condition);
    List<Condition> conditions2 = List.of(condition2);
    List<Condition> conditions3 = List.of(condition3);

    private final DynamicRule dynamicRule = new DynamicRule("Продукт1", UUID.randomUUID(), "Текст1", conditions1);
    private final DynamicRule dynamicRule2 = new DynamicRule("Продукт2", UUID.randomUUID(), "Текст2", conditions2);
    private final DynamicRule dynamicRule3 = new DynamicRule("Продукт2", UUID.randomUUID(), "Текст3", conditions3);
    List<DynamicRule> dynamicRules = List.of(dynamicRule, dynamicRule2, dynamicRule3);


    private final Transaction transaction1 = new Transaction("CREDIT", "DEPOSIT", 100_000);
    private final Transaction transaction2 = new Transaction("DEBIT", "DEPOSIT", 100_000);
    private final Transaction transaction3 = new Transaction("INVEST", "DEPOSIT", 100_000);
    List<Transaction> transactions = List.of(transaction1, transaction2, transaction3);


    @Test
    @DisplayName("Вывод рекомендаций")
    void getRecommendations() {
        when(recommendationsRepository.getTransactions(any(UUID.class))).thenReturn(transactions);
        when(dynamicRuleRepository.findAll()).thenReturn(dynamicRules);

        //test
        Collection<DynamicRule> actual = recommendationServiceImpl.getRecommendations(UUID.randomUUID()).getDynamicRules();
        Collection<DynamicRule> expected = dynamicRules;

        //check
        assertEquals(expected, actual);
        verify(dynamicRuleRepository, times(1)).findAll();
        verify(recommendationsRepository, times(dynamicRules.size())).getTransactions(any(UUID.class));


    }

    @Test
    @DisplayName(("Вывод транзакций"))
    void getTransaction() {
        when(recommendationsRepository.getTransactions(any(UUID.class))).thenReturn(transactions);

        //test
        List<Transaction> expected = transactions;
        List<Transaction> actual = recommendationServiceImpl.getTransaction(UUID.randomUUID());

        //check
        assertEquals(expected, actual);
        verify(recommendationsRepository, times(1)).getTransactions(any(UUID.class));

    }
}