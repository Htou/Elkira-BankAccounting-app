package org.example.finance;

import com.google.inject.Inject;
import org.example.model.Transaction;
import org.example.model.TransactionList;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FinancialAccount {
    private BigDecimal accountBalance;
    private BigDecimal savingsBalance;
    private BigDecimal debtAmount;
    private TransactionList transactions;

    @Inject
    public FinancialAccount(TransactionList transactions) {
        this.accountBalance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        this.savingsBalance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        this.debtAmount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        this.transactions = transactions;
    }

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

    public synchronized void depositToAccountBalance(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.accountBalance = accountBalance.add(amount).setScale(2, RoundingMode.HALF_EVEN);;
    }

    public synchronized void withdrawFromAccountBalance(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0 || amount.compareTo(accountBalance) > 0) {
            throw new IllegalArgumentException("Invalid withdrawal amount");
        }
        this.accountBalance = accountBalance.subtract(amount).setScale(2, RoundingMode.HALF_EVEN);;
    }

    public synchronized void depositToSavingsBalance(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.savingsBalance = savingsBalance.add(amount).setScale(2, RoundingMode.HALF_EVEN);;
    }

    public synchronized void withdrawFromSavingsBalance(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0 || amount.compareTo(savingsBalance) > 0) {
            throw new IllegalArgumentException("Insufficient funds for savings withdrawal");
        }
        this.savingsBalance = savingsBalance.subtract(amount).setScale(2, RoundingMode.HALF_EVEN);;
    }

    public synchronized void payDebt(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0 || amount.compareTo(debtAmount) > 0) {
            throw new IllegalArgumentException("Invalid payment amount");
        }
        this.debtAmount = debtAmount.subtract(amount).setScale(2, RoundingMode.HALF_EVEN);;
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
                depositToAccountBalance(amount);
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
                    withdrawFromAccountBalance(amount);
                    depositToSavingsBalance(amount);
                } else {
                    throw new IllegalArgumentException("Insufficient funds for savings deposit");
                }
                break;
            case SAVINGS_WITHDRAWAL:
                if (amount.compareTo(savingsBalance) <= 0) {
                    withdrawFromSavingsBalance(amount);
                    depositToAccountBalance(amount);
                } else {
                    throw new IllegalArgumentException("Insufficient funds for savings withdrawal");
                }
                break;
            case DEBT_PAYMENT:
                if (amount.compareTo(debtAmount) <= 0) {
                    withdrawFromAccountBalance(amount);
                    payDebt(amount);
                } else {
                    throw new IllegalArgumentException("Insufficient debt amount for payment");
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported transaction type: " + transaction.getType());
        }
    }
}

