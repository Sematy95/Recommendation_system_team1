package pro.sky.recommendation_service.domain.enums;

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
