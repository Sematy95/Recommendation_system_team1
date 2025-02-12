package pro.sky.recommendation_service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "[EXCEPTION] Condition Not Found Exception")
public class ConditionNotFoundException extends RuntimeException{
    private static final Logger logger = LoggerFactory.getLogger(ConditionNotFoundException.class);

    public ConditionNotFoundException(Long message) {
        super("Such condition does not exist: " + message);
        logger.error("Such condition does not exist: {}", message);
    }
}
