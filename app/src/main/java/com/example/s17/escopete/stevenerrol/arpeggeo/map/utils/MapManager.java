package com.example.s17.escopete.stevenerrol.arpeggeo.map.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.example.s17.escopete.stevenerrol.arpeggeo.R;
import com.example.s17.escopete.stevenerrol.arpeggeo.core.utils.AppState;
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.PlaylistRepositoryImpl;
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.ui.PlaylistEntryDialog;
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.ui.PlaylistPreviewDialog;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;

public class MapManager {
    // Starting State
    private final AppState STARTING_STATE = AppState.VIEW;

    // Starting point
    private final GeoPoint RIZAL_PARK_GEOPOINT = new GeoPoint(14.5826, 120.9787);

    private MapView mapView;
    private MapEventsOverlay mapEventsOverlay;
    private Context context;

    private FragmentManager SupportFragmentManager;
    private PlaylistRepositoryImpl playlistRepositoryImpl;

    @AssistedInject
    public MapManager(@Assisted MapView mapView, @Assisted Context context, @Assisted FragmentManager supportFragmentManager, PlaylistRepositoryImpl playlistRepositoryImpl) {
        this.mapView = mapView;
        this.context = context;
        this.SupportFragmentManager = supportFragmentManager;
        this.playlistRepositoryImpl = playlistRepositoryImpl;

        initializeMap();
    }

    public void initializeMap() {
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));

        // UI and behavior Settings
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        mapView.getOverlayManager().getTilesOverlay().setLoadingBackgroundColor(R.color.dark_layer_1);
        mapView.getOverlayManager().getTilesOverlay().setLoadingLineColor(R.color.dark_layer_2);
        mapView.setMultiTouchControls(true);

        IMapController mapController = mapView.getController();
        mapController.setZoom(12.0);
        mapController.setCenter(RIZAL_PARK_GEOPOINT);

        /* for dynamic location update */
//        requestPermissionsIfNecessary(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION});
//        MyLocationNewOverlay locationOverlay
//                = new MyLocationNewOverlay(new GpsMyLocationProvider(context), map);
//        locationOverlay.enableMyLocation();
//        map.getOverlays().add(locationOverlay);

        /* listener for map clicks */
        mapEventsOverlay = new MapEventsOverlay(new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                PlaylistEntryDialog playlistEntryDialog = new PlaylistEntryDialog();
                playlistEntryDialog.show(SupportFragmentManager, "PlaylistEntryDialog");
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        });

        updateMap(STARTING_STATE);
    }

    public void updateMap(AppState appState) {
        /* Update Map listener */
        mapView.getOverlays().clear();
        if (appState == AppState.VIEW) {
            mapView.getOverlays().remove(mapEventsOverlay);
        } else {
            mapView.getOverlays().add(mapEventsOverlay);
        }

        /* Update User Marker */
        Marker userMarker = new Marker(mapView);
        userMarker.setPosition(new GeoPoint(14.5633, 120.9949));

        userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        Drawable userIcon = ContextCompat.getDrawable(context, R.drawable.ic_user_marker);
        userIcon.setTint(ContextCompat.getColor(context, R.color.blue));
        userMarker.setIcon(userIcon);
        mapView.getOverlays().add(userMarker);

        /* Update Playlist Markers */
        for (int i = 0; i < playlistRepositoryImpl.getAllPlaylists().size(); i++) {
            String playlistName = playlistRepositoryImpl.getPlaylistNameByIndex(i);

            Marker marker = new Marker(mapView);
            marker.setPosition(new GeoPoint(
                    playlistRepositoryImpl.getPlaylistLatitude(playlistName),
                    playlistRepositoryImpl.getPlaylistLongitude(playlistName))
            );

            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker, MapView mapView) {
                    if(appState == AppState.VIEW) {
                        PlaylistPreviewDialog playlistPreviewDialog = new PlaylistPreviewDialog();

                        Bundle bundle = new Bundle();
                        bundle.putString("playlistName", playlistName);
                        playlistPreviewDialog.setArguments(bundle);

                        playlistPreviewDialog.show(SupportFragmentManager, "PlaylistPreviewDialog");

                    }

                    return false;
                }
            });

            Drawable icon = ContextCompat.getDrawable(context, R.drawable.ic_marker);
            icon.setTint(ContextCompat.getColor(context, R.color.dark_layer_1));
            marker.setIcon(icon);
            mapView.getOverlays().add(marker);
        }
    }

    @AssistedFactory
    public interface Factory {
        MapManager create(MapView mapView, Context context, FragmentManager supportFragmentManager);
    }
}
