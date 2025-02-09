package pro.sky.recommendation_service.exception;

public class DynamicRuleNotFoundException extends RuntimeException {

    public DynamicRuleNotFoundException(Long message) {
        super("Such dynamic rule was not found: " + message);
    }
}
