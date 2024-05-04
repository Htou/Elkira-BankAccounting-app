package org.example.finance.interfaces;
import org.example.finance.Transaction; // Ensure this import matches the actual package path of Transaction
import java.math.BigDecimal;

public interface IBalanceManager {

    BigDecimal depositToAccountBalance(BigDecimal currentBalance, BigDecimal amount);

    BigDecimal withdrawFromAccountBalance(BigDecimal currentBalance, BigDecimal amount);

    BigDecimal depositToSavingsBalance(BigDecimal currentBalance, BigDecimal amount);

    BigDecimal withdrawFromSavingsBalance(BigDecimal currentBalance, BigDecimal amount);

    BigDecimal payDebt(BigDecimal currentDebt, BigDecimal amount);
}