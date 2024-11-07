package com.example.s17.escopete.stevenerrol.arpeggeo.playlist.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s17.escopete.stevenerrol.arpeggeo.R;
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.Playlist;
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.PlaylistRepositoryImpl;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.Tag;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.TagRepositoryImpl;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.ui.TagAdapter;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.ui.TagEditDialog;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.ui.TagEntryDialog;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PlaylistDetailsActivity extends AppCompatActivity {
    @Inject
    TagRepositoryImpl tagRepositoryImpl;

    @Inject
    PlaylistRepositoryImpl playlistRepositoryImpl;

    LinearLayout activityHeader;
    ScrollView scrollView;
    ImageView playlistImage;
    TextView playlistNameView;
    TextView playlistUrl;
    RecyclerView recyclerTagList;
    LinearLayout activityFooter;
    LinearLayout addTagContainer;
    LinearLayout editTagContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_details);

        /* adjust padding based on top system bars */
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_layout), (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(v.getPaddingStart(), systemBars.top, v.getPaddingEnd(), v.getPaddingBottom());
                    return insets;
                }
        );

        initializeActivity();
    }

    public void initializeActivity() {
        activityHeader = findViewById(R.id.activity_header);

        scrollView = findViewById(R.id.scroll_view);
        playlistImage = findViewById(R.id.playlist_image);
        playlistNameView = findViewById(R.id.playlist_name);
        playlistUrl = findViewById(R.id.playlist_url);
        recyclerTagList = findViewById(R.id.recycler_tag_list);

        activityFooter = findViewById(R.id.activity_footer);
        addTagContainer = findViewById(R.id.add_tag_container);
        editTagContainer = findViewById(R.id.edit_tag_container);

        Intent intent = getIntent();
        String playlistName = intent.getStringExtra("playlistName");

        playlistImage.setImageResource(playlistRepositoryImpl.getPlaylistImage(playlistName));
        playlistNameView.setText(playlistName);

        playlistUrl.setText(Html.fromHtml(
                getString(R.string.open_in_spotify, playlistRepositoryImpl.getPlaylistUrl(playlistName)),
                Html.FROM_HTML_MODE_LEGACY));
        playlistUrl.setMovementMethod(LinkMovementMethod.getInstance());

        populateTagListRecycler(playlistName);
    }

    private void populateTagListRecycler(String playlistName) {
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);

        recyclerTagList.suppressLayout(true);
        recyclerTagList.setLayoutManager(flexboxLayoutManager);

        TagAdapter tagAdapter = new TagAdapter(tagRepositoryImpl, playlistRepositoryImpl, PlaylistDetailsActivity.this);
        recyclerTagList.setAdapter(tagAdapter);
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