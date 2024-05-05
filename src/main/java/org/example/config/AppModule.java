package org.example.config;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.example.model.finance.utility.BalanceManager;
import org.example.model.finance.utility.TransactionProcessor;
import org.example.model.finance.interfaces.IBalanceManager;
import org.example.model.finance.interfaces.ITransactionProcessor;

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