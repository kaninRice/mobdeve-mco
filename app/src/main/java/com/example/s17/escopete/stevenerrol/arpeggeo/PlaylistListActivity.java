package com.example.s17.escopete.stevenerrol.arpeggeo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlaylistListActivity extends AppCompatActivity {
    private ArrayList<Playlist> playlistList;

    LinearLayout activityHeader;
    RecyclerView recyclerPlaylistListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_list);

        /* adjust padding based on top system bars */
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activityLayout), (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(v.getPaddingStart(), systemBars.top + v.getPaddingTop(), v.getPaddingEnd(), v.getPaddingBottom());
                    return insets;
                }
        );

        activityHeader = findViewById(R.id.activityHeader);
        populatePlaylistListRecycler();
    }

    public void populatePlaylistListRecycler() {
        Intent intent = getIntent();
        playlistList = intent.getParcelableArrayListExtra("playlistList");

        recyclerPlaylistListView = findViewById(R.id.recyclerPlaylistList);
        recyclerPlaylistListView.setHasFixedSize(true);
        recyclerPlaylistListView.setLayoutManager(new LinearLayoutManager(this));

        PlaylistAdapter playlistAdapter = new PlaylistAdapter(playlistList, PlaylistListActivity.this);
        recyclerPlaylistListView.setAdapter(playlistAdapter);
    }

    public void closeActivity(View v) {
        finish();
    }
}