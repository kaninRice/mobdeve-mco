package com.example.s17.escopete.stevenerrol.arpeggeo.di;

import android.content.Context;

import org.osmdroid.views.MapView;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public class MapModule {

    @Provides
    public MapView provideMapView(Context context) {
        return new MapView(context);
    }
}
