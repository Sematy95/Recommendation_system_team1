package pro.sky.recommendation_service.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.recommendation_service.domain.DynamicRule;
import pro.sky.recommendation_service.domain.Statistic;
import pro.sky.recommendation_service.exception.StatisticNotFoundException;
import pro.sky.recommendation_service.repository.StatisticRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatisticServiceImplTest {
    @Mock
    private StatisticRepository statisticRepository;

    @InjectMocks
    private StatisticServiceImpl statisticService;

    private final Statistic statistic = new Statistic(null, 0L);

    @Test
    @DisplayName("Сохранение статистики")
    public void shouldAddStatistic() {
        when(statisticRepository.save(any())).thenReturn(null);

        statisticService.addStat(new DynamicRule(), 0);

        verify(statisticRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Удаление статистики - положительный тест")
    public void shouldDeleteStatistic() {
        doNothing().when(statisticRepository).deleteById(any());
        when(statisticRepository.existsById(anyLong())).thenReturn(true);

        statisticService.deleteStat(statistic.getId());

        verify(statisticRepository, times(1)).deleteById(any());
        verify(statisticRepository, times(1)).existsById(anyLong());
    }

    @Test
    @DisplayName("Удаление статистики - отрицательный тест")
    public void shouldThrowExceptionWhenDeleteStatistic() {
        when(statisticRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(StatisticNotFoundException.class, () -> statisticService.deleteStat(statistic.getId()));

        verify(statisticRepository, times(0)).deleteById(any());
        verify(statisticRepository, times(1)).existsById(anyLong());
    }

    @Test
    @DisplayName("Инкремент счетчика")
    public void shouldIncrementCount() {
        when(statisticRepository.save(any())).thenReturn(null);
        when(statisticRepository.findById(anyLong())).thenReturn(Optional.of(statistic));

        statisticService.incrementCount(statistic.getId());

        assertEquals(statistic.getCount(), 1);

        verify(statisticRepository, times(1)).save(any());
        verify(statisticRepository, times(1)).findById(anyLong());
    }
}
