package pro.sky.recommendation_service.domain;

import java.util.Objects;

/**
 * Represents a transaction with a type, product type, and amount.
 */

public class Transaction {
    private String ProductType;
    private String transactionType;
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
    public Transaction(String productType, String transactionType, int amount) {
        this.ProductType = productType;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
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
        return Objects.equals(transactionType, that.transactionType) &&
                Objects.equals(ProductType, that.ProductType) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionType, ProductType, amount);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionType='" + transactionType + '\'' +
                ", ProductType='" + ProductType + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
