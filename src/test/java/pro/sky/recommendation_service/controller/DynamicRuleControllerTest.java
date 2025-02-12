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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pro.sky.recommendation_service.domain.DynamicRule;
import pro.sky.recommendation_service.service.impl.DynamicRuleServiceImpl;
import pro.sky.recommendation_service.service.impl.StatisticServiceImpl;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private final DynamicRule dynamicRule2 = new DynamicRule("name2", UUID.randomUUID(), "text2", null);
    private final DynamicRule dynamicRule3 = new DynamicRule("name3", UUID.randomUUID(), "text3", null);
    private final Collection<DynamicRule> dynamicRuleCollection = List.of(dynamicRule, dynamicRule2, dynamicRule3);

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Добавление динамического правила")
    void addRule() throws Exception {

        when(dynamicRuleService.addRule(dynamicRule)).thenReturn(dynamicRule);

        // test & check
        mockMvc.perform(post("/rule").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dynamicRule)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product_name").value(dynamicRule.getProduct_name()))
                .andExpect(jsonPath("$.product_text").value(dynamicRule.getProduct_text()));

        verify(dynamicRuleService, times(1)).addRule(any(DynamicRule.class));

    }

    @Test
    @DisplayName("Удаление динамического правила")
    void deleteRule() throws Exception {
        Long id = new Random().nextLong();
        doNothing().when(dynamicRuleService).deleteRule(id);

        // test & check
        mockMvc.perform(delete("/rule/{id}", id))
                .andExpect(status().isNoContent());

        verify(dynamicRuleService, times(1)).deleteRule(any(Long.class));
    }

    @Test
    @DisplayName("Выдает лист всех динамических правил из БД")
    void getAllRules() throws Exception {
        when(dynamicRuleService.getAllDynamicRules()).thenReturn(dynamicRuleCollection);

        // test & check
        mockMvc.perform(get("/rule"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(dynamicRuleService, times(1)).getAllDynamicRules();
    }
}


