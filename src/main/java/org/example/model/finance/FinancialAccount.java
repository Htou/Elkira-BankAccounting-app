package org.example.model.finance;

import com.google.inject.Inject;
import org.example.model.finance.interfaces.ITransactionProcessor;
import org.example.model.finance.interfaces.TransactionEventListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicReference;

public class FinancialAccount implements TransactionEventListener {
    private final AtomicReference<BigDecimal> accountBalance = new AtomicReference<>(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN));
    private final AtomicReference<BigDecimal> savingsBalance = new AtomicReference<>(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN));
    private final AtomicReference<BigDecimal> debtAmount = new AtomicReference<>(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN));
    private final TransactionList transactionList;
    private final ITransactionProcessor transactionProcessor;

    @Inject
    public FinancialAccount(TransactionList transactionList, ITransactionProcessor transactionProcessor) {
        this.transactionList = transactionList;
        this.transactionProcessor = transactionProcessor;
        this.transactionProcessor.addTransactionEventListener(this); // Register as a listener
    }

    public void unregisterFromProcessor() {
        this.transactionProcessor.removeTransactionEventListener(this); // Unregister as a listener
    }

    public BigDecimal getAccountBalance() {
        return accountBalance.get();
    }

    public BigDecimal getSavingsBalance() {
        return savingsBalance.get();
    }

    public BigDecimal getDebtAmount() {
        return debtAmount.get();
    }

    public TransactionList getTransactions() {
        return this.transactionList;
    }

    public void addTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        transactionProcessor.processTransaction(transaction, accountBalance, savingsBalance, debtAmount);
        transactionList.addTransaction(transaction);
    }

    @Override
    public void onTransactionProcessed(Transaction transaction, AtomicReference<BigDecimal> accountBalance, AtomicReference<BigDecimal> savingsBalance, AtomicReference<BigDecimal> debtAmount) {
        // Handle the event, for example, logging or updating a UI component
        System.out.println("Transaction processed: " + transaction);
    }
}