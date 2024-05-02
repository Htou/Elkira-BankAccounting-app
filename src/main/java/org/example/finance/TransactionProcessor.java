package org.example.finance;

import org.example.finance.interfaces.ITransactionProcessor;
import org.example.model.Transaction;
import org.example.model.TransactionList;
import java.math.BigDecimal;

public class TransactionProcessor implements ITransactionProcessor {
    private FinancialAccount financialAccount;

    // Constructor injection for FinancialAccount
    public TransactionProcessor(FinancialAccount financialAccount) {
        this.financialAccount = financialAccount;
    }

    @Override
    public void processIncome(Transaction transaction) {
        // Implementation for processing income transactions
        financialAccount.depositToAccountBalance(transaction.getAmount());
    }

    @Override
    public void processExpense(Transaction transaction) {
        // Implementation for processing expense transactions
        financialAccount.withdrawFromAccountBalance(transaction.getAmount());
    }

    @Override
    public void processSavingsDeposit(Transaction transaction) {
        // Implementation for processing savings deposit transactions
        financialAccount.depositToSavingsBalance(transaction.getAmount());
    }

    @Override
    public void processSavingsWithdrawal(Transaction transaction) {
        // Implementation for processing savings withdrawal transactions
        financialAccount.withdrawFromSavingsBalance(transaction.getAmount());
    }

    @Override
    public void processDebtPayment(Transaction transaction) {
        // Implementation for processing debt payment transactions
        financialAccount.payDebt(transaction.getAmount());
    }
}