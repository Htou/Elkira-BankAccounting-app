package org.example.model.finance;

import org.example.model.finance.utility.BalanceManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class BalanceManagerTest {

    private BalanceManager balanceManager;
    private AtomicReference<BigDecimal> accountBalance;
    private AtomicReference<BigDecimal> savingsBalance;
    private AtomicReference<BigDecimal> debtAmount;

    @BeforeEach
    void setUp() {
        balanceManager = new BalanceManager();
        accountBalance = new AtomicReference<>(BigDecimal.valueOf(1000.00));
        savingsBalance = new AtomicReference<>(BigDecimal.valueOf(500.00));
        debtAmount = new AtomicReference<>(BigDecimal.valueOf(200.00));
    }

    import java.time.LocalDateTime;

    @Test
    void testIncomeTransaction() {
        Transaction transaction = new Transaction(BigDecimal.valueOf(500.00), Transaction.Type.INCOME, LocalDateTime.now(), "Monthly salary");
        balanceManager.updateAccountBalances(transaction, accountBalance, savingsBalance, debtAmount);
        assertEquals(0, BigDecimal.valueOf(1500.00).compareTo(accountBalance.get()));
    }

    @Test
    void testExpenseTransaction() {
        // Adjusted to include LocalDateTime.now() for the date and a description
        Transaction transaction = new Transaction(BigDecimal.valueOf(300.00), Transaction.Type.EXPENSE, LocalDateTime.now(), "Grocery shopping");
        balanceManager.updateAccountBalances(transaction, accountBalance, savingsBalance, debtAmount);
        assertEquals(0, BigDecimal.valueOf(700.00).compareTo(accountBalance.get()));
    }

    @Test
    void testSavingsDeposit() {
        Transaction transaction = new Transaction(BigDecimal.valueOf(200.00), Transaction.Type.SAVINGS_DEPOSIT, LocalDateTime.now(), "Savings deposit");
        balanceManager.updateAccountBalances(transaction, accountBalance, savingsBalance, debtAmount);
        assertEquals(0, BigDecimal.valueOf(800.00).compareTo(accountBalance.get()));
        assertEquals(0, BigDecimal.valueOf(700.00).compareTo(savingsBalance.get()));
    }

    @Test
    void testSavingsWithdrawal() {
        Transaction transaction = new Transaction(BigDecimal.valueOf(100.00), Transaction.Type.SAVINGS_WITHDRAWAL, LocalDateTime.now(), "Savings withdrawal");
        balanceManager.updateAccountBalances(transaction, accountBalance, savingsBalance, debtAmount);
        assertEquals(0, BigDecimal.valueOf(1100.00).compareTo(accountBalance.get()));
        assertEquals(0, BigDecimal.valueOf(400.00).compareTo(savingsBalance.get()));
    }

    @Test
    void testDebtPayment() {
        Transaction transaction = new Transaction(BigDecimal.valueOf(150.00), Transaction.Type.DEBT_PAYMENT, LocalDateTime.now(), "Debt payment");
        balanceManager.updateAccountBalances(transaction, accountBalance, savingsBalance, debtAmount);
        assertEquals(0, BigDecimal.valueOf(850.00).compareTo(accountBalance.get()));
        assertEquals(0, BigDecimal.valueOf(50.00).compareTo(debtAmount.get()));
    }

    @Test
    void testInvalidTransactionType() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(BigDecimal.valueOf(100.00), null, LocalDateTime.now(), "Invalid transaction type");
        });
    }

    @Test
    void testInsufficientFundsForExpense() {
        Transaction transaction = new Transaction(BigDecimal.valueOf(1500.00), Transaction.Type.EXPENSE, LocalDateTime.now(), "Expense exceeding funds");
        assertThrows(IllegalArgumentException.class, () -> balanceManager.updateAccountBalances(transaction, accountBalance, savingsBalance, debtAmount));
    }
}