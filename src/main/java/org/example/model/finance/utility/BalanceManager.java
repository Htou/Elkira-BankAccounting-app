package org.example.model.finance.utility;

import org.example.model.finance.Transaction;
import org.example.model.finance.interfaces.IBalanceManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicReference;

public class BalanceManager implements IBalanceManager {

    @Override
    public synchronized void updateBalances(Transaction transaction, AtomicReference<BigDecimal> accountBalance, AtomicReference<BigDecimal> savingsBalance, AtomicReference<BigDecimal> debtAmount) {
        BigDecimal transactionAmount = transaction.getAmount();
        switch (transaction.getType()) {
            case INCOME:
                accountBalance.set(deposit(accountBalance.get(), transactionAmount));
                break;
            case EXPENSE:
                accountBalance.set(withdraw(accountBalance.get(), transactionAmount));
                break;
            case SAVINGS_DEPOSIT:
                accountBalance.set(withdraw(accountBalance.get(), transactionAmount));
                savingsBalance.set(deposit(savingsBalance.get(), transactionAmount));
                break;
            case SAVINGS_WITHDRAWAL:
                savingsBalance.set(withdraw(savingsBalance.get(), transactionAmount));
                accountBalance.set(deposit(accountBalance.get(), transactionAmount));
                break;
            case DEBT_PAYMENT:
                accountBalance.set(withdraw(accountBalance.get(), transactionAmount));
                debtAmount.set(withdraw(debtAmount.get(), transactionAmount));
                break;
            default:
                throw new IllegalArgumentException("Unsupported transaction type: " + transaction.getType());
        }
    }

    private BigDecimal deposit(BigDecimal currentBalance, BigDecimal amount) {
        return currentBalance.add(amount).setScale(2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal withdraw(BigDecimal currentBalance, BigDecimal amount) {
        if (amount.compareTo(currentBalance) > 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        return currentBalance.subtract(amount).setScale(2, RoundingMode.HALF_EVEN);
    }
}