package pro.sky.recommendation_service.domain.enums;

/**
 * Enum representing the different names or types of transactions.
 */
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
