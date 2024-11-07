package com.example.s17.escopete.stevenerrol.arpeggeo.main;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.PlaylistRepositoryImpl;
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.ui.PlaylistEntryDialog;
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.ui.PlaylistListActivity;
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.ui.PlaylistPreviewDialog;
import com.example.s17.escopete.stevenerrol.arpeggeo.R;
import com.example.s17.escopete.stevenerrol.arpeggeo.settings.ui.SettingsActivity;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.TagRepositoryImpl;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    @Inject
    TagRepositoryImpl tagRepositoryImpl;

    @Inject
    PlaylistRepositoryImpl playlistRepositoryImpl;

    private State appState = State.VIEW;

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map;
    private MapEventsOverlay mapEventsOverlay;

    EditText searchBar;
    ImageButton settingsButton;
    ImageButton addButton;
    ImageButton playlistListButton;

    enum State {
        VIEW,
        ADD
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeApp();
    }

    public void initializeApp() {
        map = findViewById(R.id.map);
        searchBar = findViewById(R.id.search_bar);
        settingsButton = findViewById(R.id.settings_button);
        addButton = findViewById(R.id.add_button);
        playlistListButton = findViewById(R.id.playlist_list_button);

        initializeMap();
    }

    public void initializeMap() {
        Context context = getApplicationContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));

        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        map.getOverlayManager().getTilesOverlay().setLoadingBackgroundColor(R.color.dark_layer_1);
        map.getOverlayManager().getTilesOverlay().setLoadingLineColor(R.color.dark_layer_2);
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(12.0);
        GeoPoint startPoint = new GeoPoint(14.5826, 120.9787);
        mapController.setCenter(startPoint);

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
                playlistEntryDialog.show(getSupportFragmentManager(), "PlaylistEntryDialog");
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        });

        updateMap();
    }

    public void updateMap() {
        /* Update Map listener */
        map.getOverlays().clear();
        if (appState == State.VIEW) {
            map.getOverlays().remove(mapEventsOverlay);
        } else {
            map.getOverlays().add(mapEventsOverlay);
        }

        /* Update User Marker */
        Marker userMarker = new Marker(map);
        userMarker.setPosition(new GeoPoint(14.5633, 120.9949));

        userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        Drawable userIcon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_user_marker, null);
        userIcon.setTint(ContextCompat.getColor(MainActivity.this, R.color.blue));
        userMarker.setIcon(userIcon);
        map.getOverlays().add(userMarker);

        /* Update Playlist Markers */
        for (int i = 0; i < playlistRepositoryImpl.getAllPlaylists().size(); i++) {
            String playlistName = playlistRepositoryImpl.getPlaylistNameByIndex(i);

            Marker marker = new Marker(map);
            marker.setPosition(new GeoPoint(
                    playlistRepositoryImpl.getPlaylistLatitude(playlistName),
                    playlistRepositoryImpl.getPlaylistLongitude(playlistName))
            );

            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker, MapView mapView) {
                    if(appState == State.VIEW) {
                        PlaylistPreviewDialog playlistPreviewDialog = new PlaylistPreviewDialog();

                        Bundle bundle = new Bundle();
                        bundle.putString("playlistName", playlistName);
                        playlistPreviewDialog.setArguments(bundle);

                        playlistPreviewDialog.show(getSupportFragmentManager(), "PlaylistPreviewDialog");

                    }

                    return false;
                }
            });

            Drawable icon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_marker, null);
            icon.setTint(ContextCompat.getColor(MainActivity.this, R.color.dark_layer_1));
            marker.setIcon(icon);
            map.getOverlays().add(marker);
        }
    }

    public void openSettings(View v) {
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
    }

    public void toggleAddActivity(View v) {
        if (appState == State.VIEW) {
            appState = State.ADD;
            addButton.setBackgroundTintList(MainActivity.this.getResources().getColorStateList(R.color.blue));
        } else {
            appState = State.VIEW;
            addButton.setBackgroundTintList(MainActivity.this.getResources().getColorStateList(R.color.dark_layer_1));
        }

        updateMap();
    }

    public void openPlaylistList(View v) {
        startActivity(new Intent(MainActivity.this, PlaylistListActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onPause() {
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (!permissionsToRequest.isEmpty()) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (checkSelfPermission(permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }

        if (!permissionsToRequest.isEmpty()) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

}