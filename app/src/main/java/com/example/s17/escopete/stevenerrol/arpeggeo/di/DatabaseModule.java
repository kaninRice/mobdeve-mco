package com.example.s17.escopete.stevenerrol.arpeggeo.di;

import android.content.Context;

import com.example.s17.escopete.stevenerrol.arpeggeo.core.data.DbHelper;
import com.example.s17.escopete.stevenerrol.arpeggeo.core.data.DbManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

/**
 * Hilt module for database dependencies
 * Provides singleton instances of database helpers and managers
 */
@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {
    @Provides
    @Singleton
    public DbHelper provideDbHelper(@ApplicationContext Context context) {
        return new DbHelper(context);
    }

    @Provides
    @Singleton
    public DbManager provideDbManager(@ApplicationContext Context context) {
        return new DbManager(context);
    }
}
