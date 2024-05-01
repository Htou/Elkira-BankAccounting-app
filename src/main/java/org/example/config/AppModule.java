package org.example.config;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class AppModule extends AbstractModule {

    @Provides
    @Singleton
    public Gson provideGson() {
        return new Gson();
    }
}