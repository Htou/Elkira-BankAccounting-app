package org.example.model.finance;

import com.google.inject.Inject;
import org.example.model.finance.interfaces.IBalanceManager;
import org.example.model.finance.interfaces.ITransactionProcessor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicReference;

public class FinancialAccount {
    private AtomicReference<BigDecimal> accountBalance = new AtomicReference<>(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN));
    private AtomicReference<BigDecimal> savingsBalance = new AtomicReference<>(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN));
    private AtomicReference<BigDecimal> debtAmount = new AtomicReference<>(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN));
    private TransactionList transactionList;
    private final ITransactionProcessor transactionProcessor;
    private final IBalanceManager balanceManager;

    @Inject
    public FinancialAccount(TransactionList transactionList, ITransactionProcessor transactionProcessor, IBalanceManager balanceManager) {
        setTransactions(transactionList);
        this.transactionProcessor = transactionProcessor;
        this.balanceManager = balanceManager;
    }

    public AtomicReference<BigDecimal> getAccountBalance() {
        return accountBalance;
    }

    public AtomicReference<BigDecimal> getSavingsBalance() {
        return savingsBalance;
    }

    public AtomicReference<BigDecimal> getDebtAmount() {
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
        balanceManager.updateAccountBalances(transaction, accountBalance, savingsBalance, debtAmount);
    }
}

