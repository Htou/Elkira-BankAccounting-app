package org.example.finance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TransactionList {
    private final List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        // Check for duplicate transactions, assuming a method exists to find a transaction by ID
        if (transactions.stream().anyMatch(t -> t.getId().equals(transaction.getId()))) {
            throw new IllegalArgumentException("Duplicate transaction ID");
        }
        // Validate transaction amount is not negative
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Transaction amount must be positive");
        }
        // Additional checks can be added here, such as date validation, type validation, etc.
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public int size() {
        return transactions.size();
    }

    public boolean removeTransaction(Transaction transaction) {
        return transactions.remove(transaction);
    }

    // Example method to find transactions by a certain criterion
    public List<Transaction> findTransactions(Predicate<Transaction> predicate) {
        return transactions.stream().filter(predicate).collect(Collectors.toList());
    }

    // Summarize transactions, e.g., calculate total amount
    public BigDecimal summarizeTransactions() {
        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}