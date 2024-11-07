package com.example.s17.escopete.stevenerrol.arpeggeo.main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.s17.escopete.stevenerrol.arpeggeo.core.utils.AppState;
import com.example.s17.escopete.stevenerrol.arpeggeo.map.utils.MapManager;
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.PlaylistRepositoryImpl;
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.ui.PlaylistListActivity;
import com.example.s17.escopete.stevenerrol.arpeggeo.R;
import com.example.s17.escopete.stevenerrol.arpeggeo.settings.ui.SettingsActivity;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.TagRepositoryImpl;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    @Inject
    MapManager.Factory mapManagerFactory;
    MapManager mapManager;

    @Inject
    TagRepositoryImpl tagRepositoryImpl;

    @Inject
    PlaylistRepositoryImpl playlistRepositoryImpl;

    public AppState appState = AppState.VIEW;

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map;
    private MapEventsOverlay mapEventsOverlay;

    EditText searchBar;
    ImageButton settingsButton;
    ImageButton addButton;
    ImageButton playlistListButton;

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

        mapManager = mapManagerFactory.create(map, this, getSupportFragmentManager());
        mapManager.initializeMap();
    }

    public void openSettings(View v) {
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
    }

    public void toggleAddActivity(View v) {
        if (appState == AppState.VIEW) {
            appState = AppState.ADD;
            addButton.setBackgroundTintList(MainActivity.this.getResources().getColorStateList(R.color.blue));
        } else {
            appState = AppState.VIEW;
            addButton.setBackgroundTintList(MainActivity.this.getResources().getColorStateList(R.color.dark_layer_1));
        }

        mapManager.updateMap(appState);
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