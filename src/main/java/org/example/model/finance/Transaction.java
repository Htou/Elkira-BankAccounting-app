package org.example.model.finance;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Transaction {
    public enum Type {
        INCOME, EXPENSE, SAVINGS_DEPOSIT, DEBT_PAYMENT, SAVINGS_WITHDRAWAL
    }

    private final String id;
    private final BigDecimal amount;
    private final Type type;
    private final LocalDateTime date;
    private final String description;

    public Transaction(BigDecimal amount, Type type, LocalDateTime date, String description) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive");
        }
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.type = type;
        this.date = Objects.requireNonNull(date, "Date cannot be null");
        this.description = description != null ? description : ""; // Ensure description is not null
    }

    public String getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Type getType() {
        return type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", type=" + type +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id.equals(that.id) &&
                amount.equals(that.amount) &&
                type == that.type &&
                date.equals(that.date) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, type, date, description);
    }
}