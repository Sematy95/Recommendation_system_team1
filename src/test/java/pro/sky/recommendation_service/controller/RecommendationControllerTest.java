package pro.sky.recommendation_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pro.sky.recommendation_service.domain.DynamicRule;
import pro.sky.recommendation_service.domain.ResponseForUser;
import pro.sky.recommendation_service.domain.Transaction;
import pro.sky.recommendation_service.service.impl.RecommendationServiceImpl;
import pro.sky.recommendation_service.service.impl.StatisticServiceImpl;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RecommendationController.class)
class RecommendationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private RecommendationServiceImpl recommendationServiceImpl;

    @MockitoBean
    private StatisticServiceImpl statisticService;

    @Autowired
    private ObjectMapper objectMapper;

    private final DynamicRule dynamicRule = new DynamicRule("name1", UUID.randomUUID(), "text1", null);
    private final DynamicRule dynamicRule2 = new DynamicRule("name2", UUID.randomUUID(), "text2", null);
    private final DynamicRule dynamicRule3 = new DynamicRule("name3", UUID.randomUUID(), "text3", null);
    private final Collection<DynamicRule> dynamicRuleCollection = List.of(dynamicRule, dynamicRule2, dynamicRule3);
    private final Transaction transaction = new Transaction("Продукт1", "Тип транзакции1", new Random().nextInt());
    private final Transaction transaction2 = new Transaction("Продукт2", "Тип транзакции2", new Random().nextInt());
    private final Transaction transaction3 = new Transaction("Продукт3", "Тип транзакции3", new Random().nextInt());
    private final List<Transaction> transactionCollection = List.of(transaction, transaction2, transaction3);



    @Test
    @DisplayName("Выдача динамических правил для пользователя по id")
    void getRecommendation() throws Exception {
        UUID userId = UUID.randomUUID();
        ResponseForUser responseForUser = new ResponseForUser(userId,dynamicRuleCollection);
        when(recommendationServiceImpl.getRecommendations(userId)).thenReturn(responseForUser);

        //test & check
        mvc.perform(get("/recommendation/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(recommendationServiceImpl, times(1)).getRecommendations(any(UUID.class));

    }

    @Test
    @DisplayName("Выдача всех транзакций пользователя по id")
    void getTransaction() throws Exception {
        UUID userId = UUID.randomUUID();
        when(recommendationServiceImpl.getTransaction(userId)).thenReturn(transactionCollection);

        //test & check
        mvc.perform(get("/transaction/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(recommendationServiceImpl, times(1)).getTransaction(any(UUID.class));



    }
}