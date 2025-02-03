package pro.sky.recommendation_service.domain;

import java.util.Objects;

public class Transaction {
    private String ProductType;
    private String transactionType;
    private int amount;

    public Transaction() {
    }

    public Transaction(String productType, String transactionType, int amount) {
        ProductType = productType;
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
        return Objects.equals(transactionType, that.transactionType) && Objects.equals(ProductType, that.ProductType) && Objects.equals(amount, that.amount);
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
