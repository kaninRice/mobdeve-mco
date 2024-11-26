package com.example.s17.escopete.stevenerrol.arpeggeo.playlist.ui;

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
public class PlaylistListActivity extends AppCompatActivity implements PlaylistAdapter.ItemSelectionListener {
    @Inject
    TagRepositoryImpl tagRepositoryImpl;

    @Inject
    PlaylistRepositoryImpl playlistRepositoryImpl;

    private LinearLayout batchDeleteView;
    private EditText searchBarView;
    RecyclerView recyclerPlaylistListView;

    private ArrayList<PlaylistWithSelectState> persistentPlaylists;
    private ArrayList<String> selectedPlaylistNames;
    private ArrayList<PlaylistWithSelectState> filteredPlaylists;

    private ListActivityState listActivityState;

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

    /**
     * Called when activity is resumed
     */
    @Override
    protected void onResume() {
        super.onResume();

        resetActivityState();
        updateRecyclerView();
    }

    /**
     * Initialize the activity; binds views to variables and sets data
     */
    public void initializeActivity() {
        batchDeleteView = findViewById(R.id.batch_delete);
        searchBarView = findViewById(R.id.search_bar);
        recyclerPlaylistListView = findViewById(R.id.recycler_playlist_list);

        selectedPlaylistNames = new ArrayList<>();

        persistentPlaylists = convertPlaylistToPlaylistWithSelectState(playlistRepositoryImpl.getAllPlaylists());
        filteredPlaylists = new ArrayList<>(persistentPlaylists);

        listActivityState = ListActivityState.HAS_NO_SELECTION;

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
                filteredPlaylists.clear();
                for (PlaylistWithSelectState playlist : persistentPlaylists) {
                    if (playlist.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredPlaylists.add(playlist);
                    }
                }

                PlaylistAdapter playlistAdapter = new PlaylistAdapter(tagRepositoryImpl, playlistRepositoryImpl,
                        filteredPlaylists, PlaylistListActivity.this, PlaylistListActivity.this);
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
                filteredPlaylists, PlaylistListActivity.this, PlaylistListActivity.this);
        recyclerPlaylistListView.setAdapter(playlistAdapter);
    }

    /**
     * Updates the recycler view
     */
    private void updateRecyclerView() {
        PlaylistAdapter playlistAdapter = new PlaylistAdapter(tagRepositoryImpl, playlistRepositoryImpl,
                filteredPlaylists, PlaylistListActivity.this, PlaylistListActivity.this);
        recyclerPlaylistListView.setAdapter(playlistAdapter);
    }

    /**
     * Deletes all the selected items based on {@code selectedPlaylistNames}
     * @param v The view that was clicked
     */
    public void batchDelete(View v) {
        playlistRepositoryImpl.deletePlaylist(selectedPlaylistNames);

        resetActivityState();
        updateRecyclerView();
    }

    /**
     * Closes the activity
     * @param v The view that was clicked
     */
    public void closeActivity(View v) {
        finish();
    }

    /**
     * Resets the activity state (Reloads playlists from local storage, remove filtered and selected
     * playlist)
     */
    private void resetActivityState() {
        persistentPlaylists = convertPlaylistToPlaylistWithSelectState(playlistRepositoryImpl.getAllPlaylists());
        filteredPlaylists = new ArrayList<>(persistentPlaylists);
        selectedPlaylistNames.clear();

        listActivityState = ListActivityState.HAS_NO_SELECTION;
        batchDeleteView.setVisibility(View.GONE);
    }

    /**
     * Converts {@link Playlist}s to {@link PlaylistWithSelectState}s
     * @param playlists The {@link Playlist}s to be converted
     * @return An {@link ArrayList} of {@link PlaylistWithSelectState}
     */
    private ArrayList<PlaylistWithSelectState> convertPlaylistToPlaylistWithSelectState(ArrayList<Playlist> playlists) {
        ArrayList<PlaylistWithSelectState> newPlaylists = new ArrayList<>();

        for (Playlist playlist : playlists) {
            newPlaylists.add(new PlaylistWithSelectState(
                    playlist,
                    PlaylistState.IS_NOT_SELECTED
            ));
        }

        return newPlaylists;
    }

    /**
     * Adds the selected item to {@code selectedPlaylistNames} and updates the view
     * @param playlistName The name of the {@link Playlist} deselected
     */
    @Override
    public void onItemSelected(String playlistName) {
        selectedPlaylistNames.add(playlistName);

        for (PlaylistWithSelectState playlist : persistentPlaylists) {
            if (playlist.getName().equals(playlistName)) {
                playlist.setSelectState(PlaylistState.IS_SELECTED);
            }
        }

        if (listActivityState == ListActivityState.HAS_NO_SELECTION) {
            listActivityState = ListActivityState.HAS_SELECTION;
            batchDeleteView.setVisibility(View.VISIBLE);
        }

        updateRecyclerView();
    }

    /**
     * Removes the deselected item in {@code selectedPlaylistNames} and updates the view
     * @param playlistName The name of the {@link Playlist} deselected
     */
    @Override
    public void onItemDeselected(String playlistName) {
        selectedPlaylistNames.remove(playlistName);

        for (PlaylistWithSelectState playlist : persistentPlaylists) {
            if (playlist.getName().equals(playlistName)) {
                playlist.setSelectState(PlaylistState.IS_NOT_SELECTED);
            }
        }

        if (selectedPlaylistNames.isEmpty()) {
            listActivityState = ListActivityState.HAS_NO_SELECTION;
            batchDeleteView.setVisibility(View.GONE);
        }

        updateRecyclerView();
    }

    /**
     * Represents the state of the list activity. Used in batch selection
     */
    enum ListActivityState {
        HAS_NO_SELECTION,
        HAS_SELECTION
    }

    /**
     * Represents the state of the playlist, whether it is selected or not
     */
    enum PlaylistState {
        IS_SELECTED,
        IS_NOT_SELECTED
    }
}