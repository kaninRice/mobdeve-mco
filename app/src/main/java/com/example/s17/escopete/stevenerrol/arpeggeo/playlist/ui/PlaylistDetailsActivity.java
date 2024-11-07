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

@AndroidEntryPoint
public class PlaylistDetailsActivity extends AppCompatActivity {
    @Inject
    TagRepositoryImpl tagRepositoryImpl;

    @Inject
    PlaylistRepositoryImpl playlistRepositoryImpl;

    private RecyclerView recyclerTagListView;

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

    public void initializeActivity() {
        ImageView playlistImageView = findViewById(R.id.playlist_image);
        TextView playlistNameView = findViewById(R.id.playlist_name);
        TextView playlistUrlView = findViewById(R.id.playlist_url);
        recyclerTagListView = findViewById(R.id.recycler_tag_list);

        Intent intent = getIntent();
        String playlistName = intent.getStringExtra("playlistName");

        playlistImageView.setImageResource(playlistRepositoryImpl.getPlaylistImage(playlistName));
        playlistNameView.setText(playlistName);

        playlistUrlView.setText(Html.fromHtml(
                getString(R.string.open_in_spotify, playlistRepositoryImpl.getPlaylistUrl(playlistName)),
                Html.FROM_HTML_MODE_LEGACY));
        playlistUrlView.setMovementMethod(LinkMovementMethod.getInstance());

        populateTagListRecycler();
    }

    private void populateTagListRecycler() {
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);

        recyclerTagListView.suppressLayout(true);
        recyclerTagListView.setLayoutManager(flexboxLayoutManager);

        TagAdapter tagAdapter = new TagAdapter(tagRepositoryImpl, playlistRepositoryImpl, PlaylistDetailsActivity.this);
        recyclerTagListView.setAdapter(tagAdapter);
    }

    public void addTag(View v) {
        TagEntryDialog tagEntryDialog = new TagEntryDialog();
        tagEntryDialog.show(getSupportFragmentManager(), "TagEntryDialog");
    }

    public void editTag(View v) {
        TagEditDialog tagEditDialog = new TagEditDialog();
        tagEditDialog.show(getSupportFragmentManager(), "TagEditDialog");
    }

    public void closeActivity(View v) {
        finish();
    }
}