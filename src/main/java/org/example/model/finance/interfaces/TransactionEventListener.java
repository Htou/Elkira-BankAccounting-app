package org.example.model.finance.interfaces;

import org.example.model.finance.Transaction;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public interface TransactionEventListener {
    void onTransactionProcessed(Transaction transaction, AtomicReference<BigDecimal> accountBalance, AtomicReference<BigDecimal> savingsBalance, AtomicReference<BigDecimal> debtAmount);
}
