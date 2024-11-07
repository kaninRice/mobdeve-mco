package com.example.s17.escopete.stevenerrol.arpeggeo.main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.s17.escopete.stevenerrol.arpeggeo.core.utils.AppState;
import com.example.s17.escopete.stevenerrol.arpeggeo.map.utils.MapManager;
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.PlaylistRepositoryImpl;
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.ui.PlaylistListActivity;
import com.example.s17.escopete.stevenerrol.arpeggeo.R;
import com.example.s17.escopete.stevenerrol.arpeggeo.settings.ui.SettingsActivity;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.TagRepositoryImpl;

import org.osmdroid.views.MapView;

import java.util.ArrayList;
import java.util.Arrays;

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
    private MapView mapView;

    private EditText searchBarView;
    private ImageButton addButtonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeApp();
    }

    private void initializeApp() {
        mapView = findViewById(R.id.map);
        searchBarView = findViewById(R.id.search_bar);
        addButtonView = findViewById(R.id.add_button);

        mapManager = mapManagerFactory.create(mapView, this, getSupportFragmentManager());
        mapManager.initializeMap();
    }

    public void openSettings(View v) {
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
    }

    public void toggleAddActivity(View v) {
        if (appState == AppState.VIEW) {
            appState = AppState.ADD;
            addButtonView.setBackgroundTintList(
                    ContextCompat.getColorStateList(MainActivity.this, R.color.blue)
            );
        } else {
            appState = AppState.VIEW;
            addButtonView.setBackgroundTintList(
                    ContextCompat.getColorStateList(MainActivity.this, R.color.dark_layer_1)
            );
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
        mapView.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onPause() {
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        mapView.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> permissionsToRequest = new ArrayList<>(Arrays.asList(permissions).subList(0, grantResults.length));
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