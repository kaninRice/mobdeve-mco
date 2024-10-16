package com.example.s17.escopete.stevenerrol.arpeggeo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private State appState = State.VIEW;

    private ArrayList<Playlist> playlistList;
    private ArrayList<Tag> tagList;

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map;

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

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        initializeApp();
    }

    public void initializeApp() {
        map = findViewById(R.id.map);
        searchBar = findViewById(R.id.search_bar);
        settingsButton = findViewById(R.id.settings_button);
        addButton = findViewById(R.id.add_button);
        playlistListButton = findViewById(R.id.playlist_list_button);

        initializeTags();
        initializePlaylist();
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

        updateMapMarkers();
    }

    // temp for MCO2
    public void initializeTags() {
        tagList = new ArrayList<>();

        tagList.add(
                new Tag("teka lang", "#04A793")
        );

        tagList.add(
                new Tag("asdf", "#000000")
        );

        tagList.add(
                new Tag("Tata", "#D035A5")
        );

        for(int i = 0; i < 50; i++) {
            tagList.add(
                    new Tag("New Tag " + i, "#FFFFFF")
            );
        }
    }

    // temp for MCO2
    public void initializePlaylist() {
        playlistList = new ArrayList<>();

        playlistList.add(
                new Playlist(
                        14.5826, 120.9787,
                        "https://open.spotify.com/playlist/37i9dQZEVXbNBz9cRCSFkY",
                        "Top 50 - Philippines",
                        R.drawable.image_playlist_cover_0,
                        tagList
                )
        );

        playlistList.add(
                new Playlist(
                        14.5648, 120.9932,
                        "https://open.spotify.com/playlist/52o2wQe2s1J59bjpCSFxby",
                        "itchy nadal",
                        R.drawable.ic_default_playlist_image,
                        tagList
                                .stream()
                                .filter(tag ->
                                        tag.getName().equals("teka lang")
                                                || tag.getName().equals("New Tag 2"))
                                .collect(Collectors.toCollection(ArrayList::new))
                )
        );

        playlistList.add(
                new Playlist(
                        14.5352, 120.9819,
                        "https://open.spotify.com/playlist/6P20B2kzD3G25bQYJ6HSPl",
                        "Byaheng UV Express",
                        R.drawable.image_playlist_cover_1,
                        new ArrayList<Tag>()
                )
        );
    }

    public void updateMapMarkers() {
        for (Playlist playlist : playlistList) {
            Marker marker = new Marker(map);
            marker.setPosition(new GeoPoint(playlist.getLatitude(), playlist.getLongitude()));

            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker, MapView mapView) {
                    PlaylistPreviewDialog playlistPreviewDialog = new PlaylistPreviewDialog();

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("playlist", playlist);
                    playlistPreviewDialog.setArguments(bundle);

                    playlistPreviewDialog.show(getSupportFragmentManager(), "PlaylistPreviewDialog");
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

    }

    public void openPlaylistList(View v) {
        Intent intent = new Intent(MainActivity.this, PlaylistListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("playlistList", playlistList);
        intent.putExtras(bundle);
        startActivity(intent);
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