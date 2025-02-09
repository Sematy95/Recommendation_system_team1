package pro.sky.recommendation_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pro.sky.recommendation_service.domain.DynamicRule;
import pro.sky.recommendation_service.service.DynamicRuleService;
import pro.sky.recommendation_service.service.impl.DynamicRuleServiceImpl;
import pro.sky.recommendation_service.service.impl.StatisticServiceImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DynamicRuleController.class)

class DynamicRuleControllerTest {

@Autowired
private MockMvc mvc;

@MockitoBean
    private DynamicRuleServiceImpl dynamicRuleService;

@MockitoBean
private StatisticServiceImpl statisticService;

@Autowired
    private ObjectMapper objectMapper;

private final DynamicRule dynamicRule = new DynamicRule("name1", UUID.randomUUID(), "text1", null);
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    MockitoAnnotations.openMocks(this);
}

    @Test
    @DisplayName("Добавление динамического правила - положительный тест")
    void addRule() throws Exception {

    when(dynamicRuleService.addRule(dynamicRule)).thenReturn(dynamicRule);

    //test & check
        mockMvc.perform(post("/rule").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dynamicRule)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product_name").value(dynamicRule.getProduct_name()))
                .andExpect(jsonPath("$.product_text").value(dynamicRule.getProduct_text()));

        verify(dynamicRuleService, times(1)).addRule(any(DynamicRule.class));


    }
}


