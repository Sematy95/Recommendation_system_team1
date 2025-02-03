package pro.sky.recommendation_service.exception;

public class ConditionNotFoundException extends RuntimeException{

    public ConditionNotFoundException(Long message) {
        super("Such condition does not exist: " + message);
    }
}
