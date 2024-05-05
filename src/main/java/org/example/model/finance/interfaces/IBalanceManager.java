package org.example.model.finance.interfaces;
import org.example.model.finance.Transaction; // Ensure this import matches the actual package path of Transaction
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public interface IBalanceManager {

    void updateAccountBalances(Transaction transaction, AtomicReference<BigDecimal> accountBalance, AtomicReference<BigDecimal> savingsBalance, AtomicReference<BigDecimal> deptAmount);
}