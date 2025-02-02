package pro.sky.recommendation_service.domain.enums;

/**
 * Enum representing the different types of financial products.
 */
public enum ProductType {
    DEBIT("DEBIT"),
    CREDIT("CREDIT"),
    INVEST("INVEST"),
    SAVING("SAVING");

    private final String value;

    public String getValue() {
        return value;
    }

    ProductType(String value) {
        this.value = value;
    }
}
