package pro.sky.recommendation_service.service;

import pro.sky.recommendation_service.domain.DynamicRule;

import java.util.Collection;

/**
 * Interface defining the contract for managing DynamicRule entities.
 */
public interface DynamicRuleService {
    /**
     * Adds a new dynamic rule.
     *
     * @param dynamicRule The DynamicRule object to be added.
     * @return The saved DynamicRule object.
     */
    DynamicRule addRule(DynamicRule dynamicRule);

    /**
     * Deletes a dynamic rule by its ID.
     *
     * @param id The ID of the dynamic rule to be deleted.
     */
    void deleteRule(long id);

    /**
     * Retrieves all dynamic rules.
     *
     * @return A collection of all DynamicRule objects.
     */
    Collection<DynamicRule> getAllDynamicRules();
}
