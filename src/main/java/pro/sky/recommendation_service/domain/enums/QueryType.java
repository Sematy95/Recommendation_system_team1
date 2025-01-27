package pro.sky.recommendation_service.domain.enums;

public enum QueryType {
    USER_OF("USER_OF"),
    ACTIVE_USER_OF("ACTIVE_USER_OF"),
    TRANSACTION_SUM_COMPARE("TRANSACTION_SUM_COMPARE"),
    TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW");

    private final String value;

    public String getValue() {
        return value;
    }

    QueryType(String value) {
        this.value = value;

    }
}
