package pro.sky.recommendation_service.service;

import pro.sky.recommendation_service.domain.DynamicRule;

import java.util.Collection;

public interface DynamicRuleService {
    DynamicRule addRule(DynamicRule dynamicRule);

    void deleteRule(long id);

    Collection<DynamicRule> getAllDynamicRules();
}
