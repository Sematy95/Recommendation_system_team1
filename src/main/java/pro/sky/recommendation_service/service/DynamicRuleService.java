package pro.sky.recommendation_service.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendation_service.domain.RequestObject;
import pro.sky.recommendation_service.repository.RuleRepository;


@Service
public class DynamicRuleService {

    private final RuleRepository ruleRepository;

    public DynamicRuleService(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(DynamicRuleService.class);

    public RequestObject addRule(RequestObject requestObject) {
        log.info("Was invoked method for adding student");
        ruleRepository.addRule(requestObject);
        return requestObject;
    }


}
