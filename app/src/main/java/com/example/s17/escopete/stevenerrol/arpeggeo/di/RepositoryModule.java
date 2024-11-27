package com.example.s17.escopete.stevenerrol.arpeggeo.di;

import com.example.s17.escopete.stevenerrol.arpeggeo.core.data.DbManager;
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.PlaylistRepository;
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.PlaylistRepositoryImpl;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.TagRepository;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.TagRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * Hilt module for repository dependencies
 * Provides singleton instances of repositories
 */
@Module
@InstallIn(SingletonComponent.class)
public class RepositoryModule {

    @Provides
    @Singleton
    public TagRepository provideTagRepository(DbManager dbManager) {
        return new TagRepositoryImpl(dbManager);
    }

    @Provides
    @Singleton
    public PlaylistRepository providePlaylistRepository(DbManager dbManager) {
        return new PlaylistRepositoryImpl(dbManager);
    }
}
