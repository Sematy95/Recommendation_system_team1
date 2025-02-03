package pro.sky.recommendation_service.domain.enums;

public enum TransactionName {

    WITHDRAW("WITHDRAW"),
    DEPOSIT("DEPOSIT");

    private final String value;

    public String getValue() {
        return value;
    }

    TransactionName(String value) {
        this.value = value;

    }
}
