package org.example.config;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.example.finance.BalanceManager;
import org.example.finance.TransactionProcessor;
import org.example.finance.interfaces.IBalanceManager;
import org.example.finance.interfaces.ITransactionProcessor;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ITransactionProcessor.class).to(TransactionProcessor.class).in(Singleton.class);
        bind(IBalanceManager.class).to(BalanceManager.class).in(Singleton.class);
        // If TransactionList requires binding, add it here. Otherwise, it's not necessary.
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new Gson();
    }
}