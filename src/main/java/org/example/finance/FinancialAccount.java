package org.example.finance;

import com.google.inject.Inject;
import org.example.finance.interfaces.IBalanceManager;
import org.example.finance.interfaces.ITransactionProcessor;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FinancialAccount {
    private BigDecimal accountBalance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
    private BigDecimal savingsBalance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
    private BigDecimal debtAmount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
    private TransactionList transactionList;
    private final ITransactionProcessor transactionProcessor;
    private final IBalanceManager balanceManager;

    @Inject
    public FinancialAccount(TransactionList transactionList, ITransactionProcessor transactionProcessor, IBalanceManager balanceManager) {
        setTransactions(transactionList);
        this.transactionProcessor = transactionProcessor;
        this.balanceManager = balanceManager;
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
        return transactionList;
    }


    public void setTransactions(TransactionList transactionList) {
        if (transactionList == null) {
            throw new IllegalArgumentException("Transactions list cannot be null");
        }
        this.transactionList = transactionList;
    }

    public void addTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        this.transactionList.addTransaction(transaction);
        updateBalances(transaction);
    }

    private synchronized void updateBalances(Transaction transaction) {
        BigDecimal amount = transaction.getAmount();
        switch (transaction.getType()) {
            case INCOME:
                balanceManager.depositToAccountBalance(accountBalance, amount);
                break;
            case EXPENSE:
                if (amount.compareTo(accountBalance) <= 0) {
                    balanceManager.withdrawFromAccountBalance(accountBalance, amount);
                } else {
                    throw new IllegalArgumentException("Insufficient funds for expense transaction");
                }
                break;
            case SAVINGS_DEPOSIT:
                if (amount.compareTo(accountBalance) <= 0) {
                    balanceManager.withdrawFromAccountBalance(accountBalance, amount);
                    balanceManager.depositToSavingsBalance(savingsBalance, amount);
                } else {
                    throw new IllegalArgumentException("Insufficient funds for savings deposit");
                }
                break;
            case SAVINGS_WITHDRAWAL:
                if (amount.compareTo(savingsBalance) <= 0) {
                    balanceManager.withdrawFromSavingsBalance(savingsBalance, amount);
                    balanceManager.depositToAccountBalance(accountBalance, amount);
                } else {
                    throw new IllegalArgumentException("Insufficient funds for savings withdrawal");
                }
                break;
            case DEBT_PAYMENT:
                if (amount.compareTo(debtAmount) <= 0) {
                    balanceManager.withdrawFromAccountBalance(accountBalance, amount);
                    balanceManager.payDebt(debtAmount, amount);
                } else {
                    throw new IllegalArgumentException("Insufficient debt amount for payment");
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported transaction type: " + transaction.getType());
        }
    }
}

