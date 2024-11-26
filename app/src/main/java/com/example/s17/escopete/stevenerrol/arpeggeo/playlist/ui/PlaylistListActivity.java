package com.example.s17.escopete.stevenerrol.arpeggeo.playlist.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s17.escopete.stevenerrol.arpeggeo.R;
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.Playlist;
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.PlaylistRepositoryImpl;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.TagRepositoryImpl;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * The activity which lists all the playlists
 * Allows opening Playlist Details Screen and batch selection
 */
@AndroidEntryPoint
public class PlaylistListActivity extends AppCompatActivity {
    @Inject
    TagRepositoryImpl tagRepositoryImpl;

    @Inject
    PlaylistRepositoryImpl playlistRepositoryImpl;

    private EditText searchBarView;
    RecyclerView recyclerPlaylistListView;
    private ArrayList<Playlist> filteredPlaylist;

    /**
     * Initializes the activity in application context
     * @param savedInstanceState Previous saved state to reconstruct if not null
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_list);

        /* Adjust padding based on top system bars */
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_layout), (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(v.getPaddingStart(), systemBars.top, v.getPaddingEnd(), v.getPaddingBottom());
                    return insets;
                }
        );

        initializeActivity();
        populatePlaylistListRecycler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateRecyclerView();
    }

    /**
     * Initialize the activity; binds views to variables and sets data
     */
    public void initializeActivity() {
        searchBarView = findViewById(R.id.search_bar);
        recyclerPlaylistListView = findViewById(R.id.recycler_playlist_list);

        filteredPlaylist = playlistRepositoryImpl.getAllPlaylists();

        searchBarView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /* none */
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /* Make "Search" hint centered but text input left-aligned */
                if (charSequence.length() == 0) {
                    searchBarView.setGravity(Gravity.CENTER);
                } else {
                    searchBarView.setGravity(Gravity.START);
                }

                /* Update playlist list based on text input */
                filteredPlaylist.clear();
                for (Playlist playlist : playlistRepositoryImpl.getAllPlaylists()) {
                    if (playlist.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredPlaylist.add(playlist);
                    }
                }

                PlaylistAdapter playlistAdapter = new PlaylistAdapter(tagRepositoryImpl, playlistRepositoryImpl,
                        filteredPlaylist, PlaylistListActivity.this);
                recyclerPlaylistListView.setAdapter(playlistAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                /* none */
            }
        });
    }

    /**
     * Populate the playlist container with playlists
     */
    public void populatePlaylistListRecycler() {
        recyclerPlaylistListView.setHasFixedSize(true);
        recyclerPlaylistListView.setLayoutManager(new LinearLayoutManager(this));

        PlaylistAdapter playlistAdapter = new PlaylistAdapter(
                tagRepositoryImpl, playlistRepositoryImpl,
                filteredPlaylist, PlaylistListActivity.this);
        recyclerPlaylistListView.setAdapter(playlistAdapter);
    }

    private void updateRecyclerView() {
        PlaylistAdapter playlistAdapter = new PlaylistAdapter(tagRepositoryImpl, playlistRepositoryImpl,
                filteredPlaylist, PlaylistListActivity.this);
        recyclerPlaylistListView.setAdapter(playlistAdapter);
    }

    /**
     * Closes the activity
     * @param v The view that was clicked
     */
    public void closeActivity(View v) {
        finish();
    }
}