package com.example.s17.escopete.stevenerrol.arpeggeo.di;

import android.content.Context;

import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.PlaylistDbHelper;
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.PlaylistDbManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {
    @Provides
    @Singleton
    public PlaylistDbHelper providePlaylistDatabaseHelper(@ApplicationContext Context context) {
        return new PlaylistDbHelper(context);
    }

    @Provides
    @Singleton
    public PlaylistDbManager providePlaylistDbManager(@ApplicationContext Context context) {
        return new PlaylistDbManager(context);
    }
}
