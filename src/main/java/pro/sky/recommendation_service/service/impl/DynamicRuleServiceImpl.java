package pro.sky.recommendation_service.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendation_service.domain.DynamicRule;
import pro.sky.recommendation_service.exception.DynamicRuleNotFoundException;
import pro.sky.recommendation_service.repository.DynamicRuleRepository;
import pro.sky.recommendation_service.service.DynamicRuleService;

import java.util.Collection;


@Service
public class DynamicRuleServiceImpl implements DynamicRuleService {

    private final DynamicRuleRepository dynamicRuleRepository;

    public DynamicRuleServiceImpl(DynamicRuleRepository dynamicRuleRepository) {
        this.dynamicRuleRepository = dynamicRuleRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(DynamicRuleServiceImpl.class);

    @Override
    public DynamicRule addRule(DynamicRule dynamicRule) {
        log.info("Was invoked method for adding dynamic rule id={} with product name - {} ", dynamicRule.getId(), dynamicRule.getProduct_name());
        return dynamicRuleRepository.save(dynamicRule);
    }

    @Override
    public void deleteRule(long id) {
        try {
            DynamicRule dynamicRuleForDelete = dynamicRuleRepository.findById(id).orElseThrow(() -> new DynamicRuleNotFoundException(id));
            dynamicRuleRepository.delete(dynamicRuleForDelete);
            log.info("Was invoked method for removing dynamic rule with id={} ", dynamicRuleForDelete.getId());

        } catch (DynamicRuleNotFoundException e) {
            log.error("Could not find dynamic rule with id=" + id, e);
            throw e;
        }
    }

    @Override
    public Collection<DynamicRule> getAllDynamicRules() {
        return dynamicRuleRepository.findAll();
    }


}
