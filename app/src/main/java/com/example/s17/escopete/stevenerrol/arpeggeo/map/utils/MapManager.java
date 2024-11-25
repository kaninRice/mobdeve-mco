package com.example.s17.escopete.stevenerrol.arpeggeo.map.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

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

import javax.inject.Singleton;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * Manages map-related functions
 */
public class MapManager {
    /* Initial map variables */
    private final AppState STARTING_STATE = AppState.VIEW;
    private final GeoPoint STARTING_GEOPOINT_ZOOM = new GeoPoint(14.5826, 120.9787);
    private final GeoPoint STARTING_GEOPOINT_USER = new GeoPoint(14.5507, 121.0286);
    private final Double STARTING_ZOOM = 12.0;

    private final MapView mapView;
    private MapEventsOverlay mapEventsOverlay;

    private final Context context;
    private final FragmentManager SupportFragmentManager;

    private final PlaylistRepositoryImpl playlistRepositoryImpl;

    /**
     * Creates an instance of {@code MapManager}
     * @param mapView The map view to be managed
     * @param context The application context
     * @param supportFragmentManager The supportFragmentManager for fragment transactions
     * @param playlistRepositoryImpl The repository that provides playlists;
     *                               Used for displaying the playlist markers in the map
     */
    @AssistedInject
    public MapManager(@Assisted MapView mapView, @Assisted Context context, @Assisted FragmentManager supportFragmentManager, PlaylistRepositoryImpl playlistRepositoryImpl) {
        this.mapView = mapView;
        this.context = context;
        this.SupportFragmentManager = supportFragmentManager;
        this.playlistRepositoryImpl = playlistRepositoryImpl;

        initializeMap();
    }

    /**
     * Initializes the map UI, behavior, and initial settings and onClick listeners
     */
    public void initializeMap() {
        Configuration.getInstance().load(context, context.getSharedPreferences("osmdroid_preferences", Context.MODE_PRIVATE));

        /* UI and behavior Settings */
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        mapView.getOverlayManager().getTilesOverlay().setLoadingBackgroundColor(R.color.dark_layer_1);
        mapView.getOverlayManager().getTilesOverlay().setLoadingLineColor(R.color.dark_layer_2);
        mapView.setMultiTouchControls(true);

        /* Initial Map Settings */
        IMapController mapController = mapView.getController();
        mapController.setZoom(STARTING_ZOOM);
        mapController.setCenter(STARTING_GEOPOINT_ZOOM);

        /* For dynamic location update */
//        requestPermissionsIfNecessary(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION});
//        MyLocationNewOverlay locationOverlay
//                = new MyLocationNewOverlay(new GpsMyLocationProvider(context), map);
//        locationOverlay.enableMyLocation();
//        map.getOverlays().add(locationOverlay);

        /* Listener fro map clicks */
        mapEventsOverlay = new MapEventsOverlay(new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                PlaylistEntryDialog playlistEntryDialog = new PlaylistEntryDialog();

                Bundle args = new Bundle();

                args.putDouble("latitude", p.getLatitude());
                args.putDouble("longitude", p.getLongitude());
                playlistEntryDialog.setArguments(args);

                playlistEntryDialog.show(SupportFragmentManager, "PlaylistEntryDialog");
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                // Handle long press if needed, e.g., remove marker
                return false;
            }
        });

        updateMap(STARTING_STATE);
    }

    /**
     * Updates map based on appState
     * @param appState The appState to base map updates on
     */
    public void updateMap(AppState appState) {
        /* Update Map listener */
        mapView.getOverlays().clear();
        if (appState == AppState.VIEW) {
            mapView.getOverlays().remove(mapEventsOverlay);
        } else {
            mapView.getOverlays().add(mapEventsOverlay);
        }

        updateUserMarker();
        updatePlaylistMarkers(appState);
    }

    /**
     * Updates user marker
     */
    private void updateUserMarker() {
        Marker userMarker = new Marker(mapView);
        userMarker.setPosition(STARTING_GEOPOINT_USER); // TODO: Set position based on current position

        userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        Drawable userIcon = ContextCompat.getDrawable(context, R.drawable.ic_user_marker);

        if (userIcon != null) {
            userIcon.setTint(ContextCompat.getColor(context, R.color.blue));
        }

        userMarker.setIcon(userIcon);
        mapView.getOverlays().add(userMarker);
    }

    /**
     * Updates playlist markers
     * @param appState The appState to base what on click events will be executed
     */
    private void updatePlaylistMarkers(AppState appState) {
        for (int i = 0; i < playlistRepositoryImpl.getAllPlaylists().size(); i++) {
            String playlistName = playlistRepositoryImpl.getPlaylistNameByIndex(i);

            Marker marker = new Marker(mapView);
            marker.setPosition(new GeoPoint(
                    playlistRepositoryImpl.getPlaylistLatitude(playlistName),
                    playlistRepositoryImpl.getPlaylistLongitude(playlistName)
                )
            );

            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            marker.setOnMarkerClickListener((marker1, mapView) -> {
                if(appState == AppState.VIEW) {
                    PlaylistPreviewDialog playlistPreviewDialog = new PlaylistPreviewDialog();

                    Bundle bundle = new Bundle();
                    bundle.putString("playlistName", playlistName);
                    playlistPreviewDialog.setArguments(bundle);

                    playlistPreviewDialog.show(SupportFragmentManager, "PlaylistPreviewDialog");

                }

                return false;
            });

            Drawable playlistIcon = ContextCompat.getDrawable(context, R.drawable.ic_marker);

            if (playlistIcon != null) {
                playlistIcon.setTint(ContextCompat.getColor(context, R.color.dark_layer_1));
            }

            marker.setIcon(playlistIcon);
            mapView.getOverlays().add(marker);
        }
    }

    /**
     * Factory interface for creating instances of {@code MapManager}
     */
    @AssistedFactory
    public interface Factory {
        MapManager create(MapView mapView, Context context, FragmentManager supportFragmentManager);
    }
}
