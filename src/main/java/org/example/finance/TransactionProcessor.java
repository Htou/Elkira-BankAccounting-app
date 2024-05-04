package org.example.finance;

import org.example.finance.interfaces.ITransactionProcessor;

public class TransactionProcessor implements ITransactionProcessor {

    @Override
    public void processTransaction(Transaction transaction) {
        switch (transaction.getType()) {
            case INCOME:
                financialAccount.depositToAccountBalance(transaction.getAmount());
                break;
            case EXPENSE:
                financialAccount.withdrawFromAccountBalance(transaction.getAmount());
                break;
            case SAVINGS_DEPOSIT:
                financialAccount.depositToSavingsBalance(transaction.getAmount());
                break;
            case SAVINGS_WITHDRAWAL:
                financialAccount.withdrawFromSavingsBalance(transaction.getAmount());
                break;
            case DEBT_PAYMENT:
                financialAccount.payDebt(transaction.getAmount());
                break;
            default:
                throw new IllegalArgumentException("Unsupported transaction type: " + transaction.getType());
        }
    }
}
}