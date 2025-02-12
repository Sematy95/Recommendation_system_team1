package pro.sky.recommendation_service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "[EXCEPTION] Statistic Not Found Exception")
public class StatisticNotFoundException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(StatisticNotFoundException.class);

    public StatisticNotFoundException(Long message) {
        super("Such statistic was not found: " + message);
        logger.error("Such statistic was not found: {}", message);
    }
}
