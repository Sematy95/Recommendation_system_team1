package pro.sky.recommendation_service.domain.enums;

/**
 * Enum representing the different types of comparisons that can be performed.
 */
public enum CompareType {
    BIGGER(">"),
    SMALLER("<"),
    EQUAL("="),
    BIGGER_OR_EQUAL(">="),
    SMALLER_OR_EQUAL("<=");

    private final String value;

    public String getValue() {
        return value;
    }

    CompareType(String value) {
        this.value = value;
    }
}
