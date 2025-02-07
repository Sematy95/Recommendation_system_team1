package pro.sky.recommendation_service.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.recommendation_service.domain.Condition;
import pro.sky.recommendation_service.domain.DynamicRule;
import pro.sky.recommendation_service.domain.Statistic;
import pro.sky.recommendation_service.dto.StatisticObject;
import pro.sky.recommendation_service.repository.StatisticRepository;
import pro.sky.recommendation_service.service.StatisticService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.ExpectedCount.once;

@ExtendWith(MockitoExtension.class)
public class StatisticServiceImplTest {

    @Mock
    private StatisticRepository statisticRepository;

    @InjectMocks
    private StatisticServiceImpl statisticService;

    private final Statistic statistic = new Statistic(null, 0L);

    @Test
    @DisplayName("Сохранение статистики")
    public void addStat() {
        when(statisticRepository.save(any())).thenReturn(null);

        statisticService.addStat(new DynamicRule(), 0);

        verify(statisticRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Удаление статистики")
    public void deleteStat() {
        doNothing().when(statisticRepository).deleteById(any());

        statisticService.deleteStat(statistic.getId());

        verify(statisticRepository, times(1)).deleteById(any());
    }

    @Test
    @DisplayName("Инкремент счетчика")
    public void incrementCount() {
        when(statisticRepository.save(any())).thenReturn(null);
        when(statisticRepository.findById(anyLong())).thenReturn(Optional.of(statistic));

        statisticService.incrementCount(statistic.getId());

        assertEquals(statistic.getCount(), 1);

        verify(statisticRepository, times(1)).save(any());
        verify(statisticRepository, times(1)).findById(anyLong());
    }

}
