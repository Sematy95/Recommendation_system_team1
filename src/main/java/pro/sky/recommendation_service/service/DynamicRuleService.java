package pro.sky.recommendation_service.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendation_service.domain.Condition;
import pro.sky.recommendation_service.repository.RuleRepository;


@Service
public class DynamicRuleService {

    private final RuleRepository ruleRepository;

    public DynamicRuleService(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(DynamicRuleService.class);

    public Condition addRule(Condition condition) {
        log.info("Was invoked method for adding student");
        ruleRepository.addRule(condition);
        return condition;
    }


}
