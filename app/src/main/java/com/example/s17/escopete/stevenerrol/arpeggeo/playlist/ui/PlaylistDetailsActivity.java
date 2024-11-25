package com.example.s17.escopete.stevenerrol.arpeggeo.playlist.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s17.escopete.stevenerrol.arpeggeo.R;
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.PlaylistRepositoryImpl;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.TagRepositoryImpl;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.ui.TagAdapter;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.ui.TagEditDialog;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.ui.TagEntryDialog;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * The activity which displays all playlist information
 * Allows adding, editing, and removing tags from the playlist
 */
@AndroidEntryPoint
public class PlaylistDetailsActivity extends AppCompatActivity {
    @Inject
    TagRepositoryImpl tagRepositoryImpl;

    @Inject
    PlaylistRepositoryImpl playlistRepositoryImpl;

    private RecyclerView recyclerTagListView;
    private TextView deleteButtonView;

    private String playlistName;

    /**
     * Initializes the activity in application context
     * @param savedInstanceState Previous saved state to reconstruct if not null
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_details);

        /* Adjust padding based on top system bars */
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_layout), (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(v.getPaddingStart(), systemBars.top, v.getPaddingEnd(), v.getPaddingBottom());
                    return insets;
                }
        );

        initializeActivity();
    }

    /**
     * Initialize the activity; binds views to variables and sets data
     */
    public void initializeActivity() {
        ImageView playlistImageView = findViewById(R.id.playlist_image);
        TextView playlistNameView = findViewById(R.id.playlist_name);
        TextView playlistUrlView = findViewById(R.id.playlist_url);
        recyclerTagListView = findViewById(R.id.recycler_tag_list);

        Intent intent = getIntent();
        playlistName = intent.getStringExtra("playlistName");

        /* Use default icon if there is no playlist image */
        if (playlistRepositoryImpl.getPlaylistImage(playlistName) != 0) {
            playlistImageView.setImageResource(playlistRepositoryImpl.getPlaylistImage(playlistName));
        } else {
            playlistImageView.setImageResource(R.drawable.ic_default_playlist_image);
        }

        playlistNameView.setText(playlistName);

        playlistUrlView.setText(Html.fromHtml(
                getString(R.string.open_in_spotify, playlistRepositoryImpl.getPlaylistUrl(playlistName)),
                Html.FROM_HTML_MODE_LEGACY));
        playlistUrlView.setMovementMethod(LinkMovementMethod.getInstance());

        populateTagListRecycler();
    }

    /**
     * Populate the tag container with tags
     */
    private void populateTagListRecycler() {
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);

        recyclerTagListView.suppressLayout(true);
        recyclerTagListView.setLayoutManager(flexboxLayoutManager);

        TagAdapter tagAdapter = new TagAdapter(tagRepositoryImpl, playlistRepositoryImpl, PlaylistDetailsActivity.this);
        recyclerTagListView.setAdapter(tagAdapter);
    }

    /**
     * Adds tag to the playlist
     * @param v The view that was clicked
     */
    public void addTag(View v) {
        TagEntryDialog tagEntryDialog = new TagEntryDialog();
        tagEntryDialog.show(getSupportFragmentManager(), "TagEntryDialog");
    }

    /**
     * Edits selected tag
     * @param v The view that was clicked
     */
    public void editTag(View v) {
        TagEditDialog tagEditDialog = new TagEditDialog();
        tagEditDialog.show(getSupportFragmentManager(), "TagEditDialog");
    }

    public void deletePlaylist(View v) {
        playlistRepositoryImpl.deletePlaylist(playlistName);
        finish();
    }

    /**
     * Closes the activity
     * @param v The view that was clicked
     */
    public void closeActivity(View v) {
        finish();
    }
}