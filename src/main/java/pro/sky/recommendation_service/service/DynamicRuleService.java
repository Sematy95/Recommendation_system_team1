package pro.sky.recommendation_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendation_service.domain.Condition;
import pro.sky.recommendation_service.domain.DynamicRule;
import pro.sky.recommendation_service.repository.DynamicRuleRepository;
import pro.sky.recommendation_service.repository.RuleRepository;

import java.util.Collection;

/**
 * Service class for managing dynamic rules.
 * This class provides methods for adding, deleting, and retrieving dynamic rules.
 */
@Service
public class DynamicRuleService {
    private final DynamicRuleRepository dynamicRuleRepository;

    public DynamicRuleService(DynamicRuleRepository dynamicRuleRepository) {
        this.dynamicRuleRepository = dynamicRuleRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(DynamicRuleService.class);

    /**
     * Adds a new dynamic rule.
     *
     * @param dynamicRule The DynamicRule object to be added.
     * @return The saved DynamicRule object.
     */
    public DynamicRule addRule(DynamicRule dynamicRule) {
        log.info("Was invoked method for adding dynamic rule");
        return dynamicRuleRepository.save(dynamicRule);
    }

    /**
     * Deletes a dynamic rule by its ID.
     *
     * @param id The ID of the dynamic rule to be deleted.
     */
    public void deleteRule(long id) {
        log.info("Was invoked method for removing dynamic rule");
        dynamicRuleRepository.deleteById(id);
    }

    /**
     * Gets all dynamic rules.
     *
     * @return A collection of all DynamicRule objects.
     */
    public Collection<DynamicRule> getAllDynamicRules() {
        return dynamicRuleRepository.findAll();
    }
}
