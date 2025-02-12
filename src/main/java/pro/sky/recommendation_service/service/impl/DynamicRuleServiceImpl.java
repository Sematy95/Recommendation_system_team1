package pro.sky.recommendation_service.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendation_service.domain.DynamicRule;
import pro.sky.recommendation_service.domain.Statistic;
import pro.sky.recommendation_service.exception.DynamicRuleNotFoundException;
import pro.sky.recommendation_service.repository.DynamicRuleRepository;
import pro.sky.recommendation_service.repository.StatisticRepository;
import pro.sky.recommendation_service.service.DynamicRuleService;

import java.util.Collection;

/**
 * Service class for managing dynamic rules.
 * This class provides methods for adding, deleting, and retrieving dynamic rules.
 */
@Service
public class DynamicRuleServiceImpl implements DynamicRuleService {
    private final DynamicRuleRepository dynamicRuleRepository;
    private final StatisticRepository statisticRepository;

    public DynamicRuleServiceImpl(DynamicRuleRepository dynamicRuleRepository, StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
        this.dynamicRuleRepository = dynamicRuleRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(DynamicRuleServiceImpl.class);

    /**
     * Метод для добавления динамического правила в БД с добавлением счетчика срабатываний со значением 0
     * @param dynamicRule динамическое правило, которое хотим добавить
     * @return добавленное динамическое правило для передачи в контроллер
     */
    @Override
    public DynamicRule addRule(DynamicRule dynamicRule) {
        log.info("Was invoked method for adding dynamic rule id={} with product name - {} ", dynamicRule.getId(), dynamicRule.getProduct_name());
        statisticRepository.save(new Statistic(dynamicRule, 0));
        return dynamicRuleRepository.save(dynamicRule);
    }

    /**
     * Метод для удаления динамического правила по id
     * @param id
     */
    @Override
    public void deleteRule(long id) {
        try {
            DynamicRule dynamicRuleForDelete = dynamicRuleRepository.findById(id).orElseThrow(() -> new DynamicRuleNotFoundException(id));
            statisticRepository.deleteByDynamicRule(dynamicRuleForDelete);
            dynamicRuleRepository.delete(dynamicRuleForDelete);
            log.info("Was invoked method for removing dynamic rule with id={} ", dynamicRuleForDelete.getId());
        } catch (DynamicRuleNotFoundException e) {
            log.error("Could not find dynamic rule with id={}", id, e);
            throw e;
        }
    }

    /**
     * Метод для вывода всех динамических правил
     * @return Лист всех динамических правил из БД
     */
    @Override
    public Collection<DynamicRule> getAllDynamicRules() {
        log.info("Was invoked method for getting all dynamic rules");
        return dynamicRuleRepository.findAll();
    }
}
