package org.example.model;

import com.google.inject.Inject;

import java.math.BigDecimal;

public class FinancialAccount {
    private BigDecimal accountBalance;
    private BigDecimal savingsBalance;
    private BigDecimal debtAmount;
    private TransactionList transactions;

    @Inject
    public FinancialAccount(TransactionList transactions) {
        this.accountBalance = BigDecimal.ZERO;
        this.savingsBalance = BigDecimal.ZERO;
        this.debtAmount = BigDecimal.ZERO;
        this.transactions = transactions;
    }

    public synchronized void depositToAccount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        accountBalance = accountBalance.add(amount);
    }

    public synchronized void withdrawFromAccount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0 || amount.compareTo(accountBalance) > 0) {
            throw new IllegalArgumentException("Invalid withdrawal amount");
        }
        accountBalance = accountBalance.subtract(amount);
    }

    public synchronized void depositToSavings(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        savingsBalance = savingsBalance.add(amount);
    }

    public synchronized void withdrawFromSavings(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0 || amount.compareTo(savingsBalance) > 0) {
            throw new IllegalArgumentException("Invalid withdrawal amount");
        }
        savingsBalance = savingsBalance.subtract(amount);
    }

    public synchronized void payDebt(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0 || amount.compareTo(debtAmount) > 0) {
            throw new IllegalArgumentException("Invalid payment amount");
        }
        debtAmount = debtAmount.subtract(amount);
    }

    // Adjust the getter methods to return BigDecimal
    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public BigDecimal getSavingsBalance() {
        return savingsBalance;
    }

    public BigDecimal getDebtAmount() {
        return debtAmount;
    }

    public TransactionList getTransactions() {
        return transactions;
    }

    public void setTransactions(TransactionList transactions) {
        if (transactions == null) {
            throw new IllegalArgumentException("Transactions list cannot be null");
        }
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        this.transactions.addTransaction(transaction);
        updateBalances(transaction);
    }

    public synchronized void updateBalances(Transaction transaction) {
        BigDecimal amount = transaction.getAmount();
        switch (transaction.getType()) {
            case INCOME:
                accountBalance = accountBalance.add(amount);
                break;
            case EXPENSE:
                if (amount.compareTo(accountBalance) <= 0) {
                    accountBalance = accountBalance.subtract(amount);
                } else {
                    throw new IllegalArgumentException("Insufficient funds for expense transaction");
                }
                break;
            case SAVINGS_DEPOSIT:
                if (amount.compareTo(accountBalance) <= 0) {
                    accountBalance = accountBalance.subtract(amount);
                    savingsBalance = savingsBalance.add(amount);
                } else {
                    throw new IllegalArgumentException("Insufficient funds for savings deposit");
                }
                break;
            case SAVINGS_WITHDRAWAL:
                if (amount.compareTo(savingsBalance) <= 0) {
                    savingsBalance = savingsBalance.subtract(amount);
                    accountBalance = accountBalance.add(amount);
                } else {
                    throw new IllegalArgumentException("Insufficient savings for withdrawal");
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported transaction type: " + transaction.getType());
        }
    }
}

