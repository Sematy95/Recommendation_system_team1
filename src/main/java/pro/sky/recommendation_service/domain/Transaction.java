package pro.sky.recommendation_service.domain;

import java.util.Objects;

/**
 * Represents a transaction with a type, product type, and amount.
 */
public class Transaction {
    private String transactionType;
    private String productType;
    private int amount;

    /**
     * Empty constructor for the Transaction class. Needed for other frameworks.
     */
    public Transaction() {
    }

    /**
     * Constructs a new Transaction object.
     *
     * @param transactionType The type of the transaction.
     * @param productType     The type of product involved in the transaction.
     * @param amount          The amount of the transaction.
     */
    public Transaction(String transactionType, String productType, int amount) {
        this.transactionType = transactionType;
        this.productType = productType;
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(transactionType, that.transactionType) && Objects.equals(productType, that.productType) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionType, productType, amount);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionType='" + transactionType + '\'' +
                ", productType='" + productType + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
