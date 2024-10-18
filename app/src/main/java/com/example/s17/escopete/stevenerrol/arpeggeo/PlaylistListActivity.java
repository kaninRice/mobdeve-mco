package com.example.s17.escopete.stevenerrol.arpeggeo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
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
    EditText searchBar;
    RecyclerView recyclerPlaylistListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_list);

        /* adjust padding based on top system bars */
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_layout), (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(v.getPaddingStart(), systemBars.top, v.getPaddingEnd(), v.getPaddingBottom());
                    return insets;
                }
        );

        initializeActivity();
        populatePlaylistListRecycler();
    }

    public void initializeActivity() {
        activityHeader = findViewById(R.id.activity_header);
        searchBar = findViewById(R.id.search_bar);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /* none */
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    searchBar.setGravity(Gravity.CENTER);
                } else {
                    searchBar.setGravity(Gravity.START);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                /* none */
            }
        });
    }

    public void populatePlaylistListRecycler() {
        Intent intent = getIntent();
        playlistList = intent.getParcelableArrayListExtra("playlistList");

        recyclerPlaylistListView = findViewById(R.id.recycler_playlist_list);
        recyclerPlaylistListView.setHasFixedSize(true);
        recyclerPlaylistListView.setLayoutManager(new LinearLayoutManager(this));

        PlaylistAdapter playlistAdapter = new PlaylistAdapter(playlistList, PlaylistListActivity.this);
        recyclerPlaylistListView.setAdapter(playlistAdapter);
    }

    public void closeActivity(View v) {
        finish();
    }
}