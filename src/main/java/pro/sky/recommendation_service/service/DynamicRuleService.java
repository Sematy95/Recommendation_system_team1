package pro.sky.recommendation_service.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendation_service.domain.Condition;
import pro.sky.recommendation_service.domain.DynamicRule;
import pro.sky.recommendation_service.repository.DynamicRuleRepository;
import pro.sky.recommendation_service.repository.RuleRepository;

import java.util.Collection;


@Service
public class DynamicRuleService {

    private final DynamicRuleRepository dynamicRuleRepository;

    public DynamicRuleService(DynamicRuleRepository dynamicRuleRepository) {
        this.dynamicRuleRepository = dynamicRuleRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(DynamicRuleService.class);

    public DynamicRule addRule(DynamicRule dynamicRule) {
        log.info("Was invoked method for adding dynamic rule id={} with product name - {} ", dynamicRule.getId(), dynamicRule.getProduct_name());
        return dynamicRuleRepository.save(dynamicRule);
    }
    public void deleteRule(long id) {
        try {
            DynamicRule dynamicRuleForDelete = dynamicRuleRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find dynamic rule with id=" + id));
            dynamicRuleRepository.delete(dynamicRuleForDelete);
            log.info("Was invoked method for removing dynamic rule with id={} ", dynamicRuleForDelete.getId());

        }catch (RuntimeException e) {
            log.error("Could not find dynamic rule with id=" + id, e);
            throw e;
        }
    }
    public Collection<DynamicRule> getAllDynamicRules() {
        return dynamicRuleRepository.findAll();
    }


}
