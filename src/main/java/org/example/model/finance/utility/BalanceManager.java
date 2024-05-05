package org.example.model.finance.utility;

import org.example.model.finance.Transaction;
import org.example.model.finance.interfaces.IBalanceManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicReference;

public class BalanceManager implements IBalanceManager {

    private synchronized BigDecimal depositToAccountBalance(BigDecimal currentBalance, BigDecimal transactionAmount) {
        validatePositiveAmount(transactionAmount);
        return currentBalance.add(transactionAmount).setScale(2, RoundingMode.HALF_EVEN);
    }

    private synchronized BigDecimal withdrawFromAccountBalance(BigDecimal currentBalance, BigDecimal transactionAmount) {
        validateAmount(currentBalance, transactionAmount);
        return currentBalance.subtract(transactionAmount).setScale(2, RoundingMode.HALF_EVEN);
    }

    private synchronized BigDecimal depositToSavingsBalance(BigDecimal currentBalance, BigDecimal transactionAmount) {
        validatePositiveAmount(transactionAmount);
        return currentBalance.add(transactionAmount).setScale(2, RoundingMode.HALF_EVEN);
    }

    private synchronized BigDecimal withdrawFromSavingsBalance(BigDecimal currentBalance, BigDecimal transactionAmount) {
        validateAmount(currentBalance, transactionAmount);
        return currentBalance.subtract(transactionAmount).setScale(2, RoundingMode.HALF_EVEN);
    }

    private synchronized BigDecimal payDebt(BigDecimal currentDebt, BigDecimal transactionAmount) {
        validateAmount(currentDebt, transactionAmount);
        return currentDebt.subtract(transactionAmount).setScale(2, RoundingMode.HALF_EVEN);
    }

    private void validatePositiveAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    private void validateAmount(BigDecimal balance, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0 || amount.compareTo(balance) > 0) {
            throw new IllegalArgumentException("Invalid amount");
        }
    }

    public synchronized void updateAccountBalances(Transaction transaction, AtomicReference<BigDecimal> accountBalance, AtomicReference<BigDecimal> savingsBalance, AtomicReference<BigDecimal> deptAmount) {
        BigDecimal transactionAmount = transaction.getAmount();
        switch (transaction.getType()) {
            case INCOME:
                accountBalance.set(depositToAccountBalance(accountBalance.get(), transactionAmount));
                break;
            case EXPENSE:
                if (transactionAmount.compareTo(accountBalance.get()) <= 0) {
                    accountBalance.set(withdrawFromAccountBalance(accountBalance.get(), transactionAmount));
                } else {
                    throw new IllegalArgumentException("Insufficient funds for expense transaction");
                }
                break;
            case SAVINGS_DEPOSIT:
                if (transactionAmount.compareTo(accountBalance.get()) <= 0) {
                    withdrawFromAccountBalance(accountBalance.get(), transactionAmount);
                    savingsBalance.set(depositToSavingsBalance(savingsBalance.get(), transactionAmount));
                } else {
                    throw new IllegalArgumentException("Insufficient funds for savings deposit");
                }
                break;
            case SAVINGS_WITHDRAWAL:
                if (transactionAmount.compareTo(savingsBalance.get()) <= 0) {
                    savingsBalance.set(withdrawFromSavingsBalance(savingsBalance.get(), transactionAmount));
                    accountBalance.set(depositToAccountBalance(accountBalance.get(), transactionAmount));
                } else {
                    throw new IllegalArgumentException("Insufficient funds for savings withdrawal");
                }
                break;
            case DEBT_PAYMENT:
                if (transactionAmount.compareTo(deptAmount.get()) <= 0) {
                    accountBalance.set(withdrawFromAccountBalance(accountBalance.get(), transactionAmount));
                    deptAmount.set(payDebt(deptAmount.get(), transactionAmount));
                } else {
                    throw new IllegalArgumentException("Insufficient debt amount for payment");
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported transaction type: " + transaction.getType());
        }
    }
}