package org.example.finance.interfaces;

import org.example.model.Transaction;
import java.math.BigDecimal;

public interface ITransactionProcessor {
    void processIncome(Transaction transaction);
    void processExpense(Transaction transaction) throws IllegalArgumentException;
    void processSavingsDeposit(Transaction transaction) throws IllegalArgumentException;
    void processSavingsWithdrawal(Transaction transaction) throws IllegalArgumentException;
    void processDebtPayment(Transaction transaction) throws IllegalArgumentException;
}

