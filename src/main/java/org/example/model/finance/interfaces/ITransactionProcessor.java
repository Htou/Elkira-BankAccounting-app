package org.example.model.finance.interfaces;

import org.example.model.finance.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public interface ITransactionProcessor {
    void processTransaction(Transaction transaction, AtomicReference<BigDecimal> accountBalance, AtomicReference<BigDecimal> savingsBalance, AtomicReference<BigDecimal> debtAmount);
    List<Transaction> getTransactionHistory();
    void addTransactionEventListener(TransactionEventListener listener);
    void removeTransactionEventListener(TransactionEventListener listener);
}