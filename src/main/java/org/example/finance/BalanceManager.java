package org.example.finance;

import org.example.finance.interfaces.IBalanceManager;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BalanceManager implements IBalanceManager {

    @Override
    public synchronized BigDecimal depositToAccountBalance(BigDecimal currentBalance, BigDecimal amount) {
        validatePositiveAmount(amount);
        return currentBalance.add(amount).setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public synchronized BigDecimal withdrawFromAccountBalance(BigDecimal currentBalance, BigDecimal amount) {
        validateAmount(currentBalance, amount);
        return currentBalance.subtract(amount).setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public synchronized BigDecimal depositToSavingsBalance(BigDecimal currentBalance, BigDecimal amount) {
        validatePositiveAmount(amount);
        return currentBalance.add(amount).setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public synchronized BigDecimal withdrawFromSavingsBalance(BigDecimal currentBalance, BigDecimal amount) {
        validateAmount(currentBalance, amount);
        return currentBalance.subtract(amount).setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public synchronized BigDecimal payDebt(BigDecimal currentDebt, BigDecimal amount) {
        validateAmount(currentDebt, amount);
        return currentDebt.subtract(amount).setScale(2, RoundingMode.HALF_EVEN);
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
}