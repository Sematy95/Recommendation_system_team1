package pro.sky.recommendation_service.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "[EXCEPTION] Is Not Valid")
public class IsNotValidException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(IsNotValidException.class);

    public IsNotValidException(Class<?> className, Long id, String message) {
        //String content = String.format("[EXCEPTION] [%s]: [%s] Is Not Valid. [%s]", className.getSimpleName(), id, message);
        super(String.format("[EXCEPTION] [%s]: [%s] Is Not Valid. [%s]", className.getSimpleName(), id, message));
        logger.error(String.format("[EXCEPTION] [%s]: [%s] Is Not Valid. [%s]", className.getSimpleName(), id, message));
    }
}
