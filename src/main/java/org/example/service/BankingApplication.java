package org.example.service;

import org.example.model.User;
import org.example.model.finance.FinancialAccount;
import org.example.model.finance.Transaction;
import org.example.model.finance.TransactionList;
import org.example.model.finance.utility.BalanceManager;
import org.example.model.finance.utility.TransactionProcessor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BankingApplication {
    private final BalanceManager balanceManager;
    private final TransactionProcessor transactionProcessor;
    private final FinancialAccount financialAccount;
    private final User user;

    public BankingApplication() {
        // Initialize everything
        this.balanceManager = new BalanceManager();
        this.transactionProcessor = new TransactionProcessor(balanceManager);

        TransactionList txList = new TransactionList();
        this.financialAccount = new FinancialAccount(txList, transactionProcessor);

        this.user = new User(
                "john_doe",
                "mySecretPassword",
                "John",
                "Doe",
                "john@example.com",
                java.time.LocalDate.of(1990, 1, 1),
                "1234 Main St",
                "+1234567890"
        );
        this.user.addFinancialAccount(financialAccount);
    }

    public void run() {
        // Add transactions, run your logic, etc.
        Transaction incomeTx = new Transaction(
                BigDecimal.valueOf(1000.00),
                Transaction.Type.INCOME,
                LocalDateTime.now(),
                "Salary"
        );
        financialAccount.addTransaction(incomeTx);

        // ... more logic, e.g., prompts or further interactions
        System.out.println("Account Balance: " + financialAccount.getAccountBalance());
    }
}
