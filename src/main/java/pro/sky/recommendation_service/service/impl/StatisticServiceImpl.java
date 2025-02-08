package pro.sky.recommendation_service.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendation_service.domain.DynamicRule;
import pro.sky.recommendation_service.domain.Statistic;
import pro.sky.recommendation_service.dto.StatisticObject;
import pro.sky.recommendation_service.exception.StatisticNotFoundException;
import pro.sky.recommendation_service.repository.StatisticRepository;
import pro.sky.recommendation_service.service.StatisticService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StatisticServiceImpl implements StatisticService {


    private static final Logger log = LoggerFactory.getLogger(StatisticServiceImpl.class);

    private final StatisticRepository statisticRepository;

    public StatisticServiceImpl(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    /**
     * Метод для сохранения статистики
     *
     * @param dynamicRule   Динамическое правило
     * @param count Счетчик
     */
    public void addStat(DynamicRule dynamicRule, long count) {
        log.info("stat addition method was invoked");

        Statistic statistic = new Statistic(dynamicRule, count);
        statisticRepository.save(statistic);
    }

    /**
     * Метод для удаления статистики по идентификатору
     *
     * @param id   Идентификатор статистики
     */
    public void deleteStat(long id) {
        log.info("stat deletion method was invoked");

        if (!statisticRepository.existsById(id)) {
            log.error("Statistic could not be found");
            throw new StatisticNotFoundException(id);
        }

        statisticRepository.deleteById(id);
    }

    /**
     * Метод для получения сохраненной статистики
     *
     * @return список сохраненной статистики
     */
    public List<StatisticObject> getAllStats() {
        log.info("get all stats method was invoked");

        return statisticRepository.findAll().stream()
                .map(statistic ->
                    new StatisticObject(statistic.getDynamicRule().getId(), statistic.getCount())
                )
                .collect(Collectors.toList());
    }

    /**
     * Метод для инкрементирования счетчика
     *
     * @param id   Идентификатор статистики
     */
    public void incrementCount(long id) {
        log.info("increment count method was invoked");

        statisticRepository.save(statisticRepository.findById(id).get().incrCount());
    }

}
