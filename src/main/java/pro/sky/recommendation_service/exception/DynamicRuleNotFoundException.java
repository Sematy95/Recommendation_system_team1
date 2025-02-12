package pro.sky.recommendation_service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "[EXCEPTION] Dynamic Rule Not Found Exception")
public class DynamicRuleNotFoundException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(DynamicRuleNotFoundException.class);

    public DynamicRuleNotFoundException(Long message) {
        super("Such dynamic rule was not found: " + message);
        logger.error("Such dynamic rule was not found: {}", message);
    }
}
