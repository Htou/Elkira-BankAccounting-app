package org.example.model.finance.utility;

import org.example.model.finance.Transaction;
import org.example.model.finance.interfaces.IBalanceManager;
import org.example.model.finance.interfaces.ITransactionProcessor;
import org.example.model.finance.interfaces.TransactionEventListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TransactionProcessor implements ITransactionProcessor {
    private final IBalanceManager balanceManager;
    private final List<Transaction> transactionHistory = new ArrayList<>();
    private final List<TransactionEventListener> listeners = new ArrayList<>();

    public TransactionProcessor(IBalanceManager balanceManager) {
        this.balanceManager = balanceManager;
    }

    @Override
    public void addTransactionEventListener(TransactionEventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeTransactionEventListener(TransactionEventListener listener) {
        listeners.remove(listener);
    }

    private void notifyTransactionProcessed(Transaction transaction, AtomicReference<BigDecimal> accountBalance, AtomicReference<BigDecimal> savingsBalance, AtomicReference<BigDecimal> debtAmount) {
        for (TransactionEventListener listener : listeners) {
            listener.onTransactionProcessed(transaction, accountBalance, savingsBalance, debtAmount);
        }
    }

    @Override
    public void processTransaction(Transaction transaction, AtomicReference<BigDecimal> accountBalance, AtomicReference<BigDecimal> savingsBalance, AtomicReference<BigDecimal> debtAmount) {
        balanceManager.updateBalances(transaction, accountBalance, savingsBalance, debtAmount);
        transactionHistory.add(transaction);
        notifyTransactionProcessed(transaction, accountBalance, savingsBalance, debtAmount);
    }

    @Override
    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }
}
