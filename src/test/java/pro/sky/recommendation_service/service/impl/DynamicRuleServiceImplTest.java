package pro.sky.recommendation_service.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister;
import pro.sky.recommendation_service.domain.DynamicRule;
import pro.sky.recommendation_service.exception.DynamicRuleNotFoundException;
import pro.sky.recommendation_service.repository.DynamicRuleRepository;
import pro.sky.recommendation_service.repository.StatisticRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DynamicRuleServiceImplTest {

    @Mock
    private DynamicRuleRepository dynamicRuleRepository;

    @Mock
    private StatisticRepository statisticRepository;

    @InjectMocks
    private DynamicRuleServiceImpl dynamicRuleService;

    private final DynamicRule dynamicRule = new DynamicRule("name1", UUID.randomUUID(), "text1", null);
    private final DynamicRule dynamicRule2 = new DynamicRule("name2", UUID.randomUUID(), "text2", null);
    private final DynamicRule dynamicRule3 = new DynamicRule("name3", UUID.randomUUID(), "text3", null);
    private Collection<DynamicRule> dynamicRules = List.of(dynamicRule, dynamicRule2, dynamicRule3);


    @Test
    @DisplayName("Добавление динамического правила  - положительный тест")
    void addRule() {
        when(dynamicRuleRepository.save(any(DynamicRule.class))).thenReturn(dynamicRule);
        //test
        DynamicRule expected = dynamicRule;
        DynamicRule actual = dynamicRuleService.addRule(dynamicRule);
        //check
        assertEquals(expected, actual);


    }

    @Test
    @DisplayName("Удаление динамического правила - положительный тест")
    void deleteRulePositive() {
        //test & check
        dynamicRule.setId(1L);
        when(dynamicRuleRepository.findById(dynamicRule.getId())).thenReturn(Optional.of(dynamicRule));
        dynamicRuleService.deleteRule(dynamicRule.getId());
        verify(dynamicRuleRepository, times(1)).delete(dynamicRule);
    }

    @Test
    @DisplayName("Удаление динамического правила - отрицательный тест")
    void deleteRuleNegative() {
        //test & check
        dynamicRule.setId(1L);
        when(dynamicRuleRepository.findById(any(Long.class)).orElse(null)).thenThrow(new DynamicRuleNotFoundException(dynamicRule.getId()));

        assertThrows(DynamicRuleNotFoundException.class, () -> {
            dynamicRuleService.deleteRule(any(Long.class));
        });
    }


    @Test
    @DisplayName("Вывод всех динамических правил - положительный тест")
    void getAllDynamicRules() {
        when(dynamicRuleRepository.findAll()).thenReturn((List<DynamicRule>) dynamicRules);

        //test
        Collection<DynamicRule> expected = dynamicRules;
        Collection<DynamicRule> actual = dynamicRuleService.getAllDynamicRules();
        assertEquals(expected, actual);
        verify(dynamicRuleRepository, times(1)).findAll();
    }
}