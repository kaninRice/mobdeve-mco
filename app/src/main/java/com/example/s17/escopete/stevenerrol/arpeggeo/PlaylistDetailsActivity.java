package com.example.s17.escopete.stevenerrol.arpeggeo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;

public class PlaylistDetailsActivity extends AppCompatActivity {
    ArrayList<Tag> tagList;

    LinearLayout activityHeader;
    ScrollView scrollView;
    ImageView playlistImage;
    TextView playlistName;
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activityLayout), (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(v.getPaddingStart(), systemBars.top, v.getPaddingEnd(), v.getPaddingBottom());
                    return insets;
                }
        );

        initializeActivity();
    }

    public void initializeActivity() {
        activityHeader = findViewById(R.id.activityHeader);

        scrollView = findViewById(R.id.scrollView);
        playlistImage = findViewById(R.id.playlistImage);
        playlistName = findViewById(R.id.playlistName);
        playlistUrl = findViewById(R.id.playlistUrl);
        recyclerTagList = findViewById(R.id.recyclerTagList);

        activityFooter = findViewById(R.id.activityFooter);
        addTagContainer = findViewById(R.id.add_tag_container);
        editTagContainer = findViewById(R.id.edit_tag_container);

        Intent intent = getIntent();
        Playlist playlist = intent.getParcelableExtra("playlist");

        playlistImage.setImageResource(playlist.getImage());
        playlistName.setText(playlist.getName());

        playlistUrl.setText(Html.fromHtml(
                getString(R.string.open_in_spotify, playlist.getUrl()),
                Html.FROM_HTML_MODE_LEGACY));
        playlistUrl.setMovementMethod(LinkMovementMethod.getInstance());

        populateTagListRecycler(intent);
    }

    private void populateTagListRecycler(Intent intent) {
        tagList = intent.getParcelableArrayListExtra("tagList");
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setJustifyContent(JustifyContent.CENTER);

        recyclerTagList.suppressLayout(true);
        recyclerTagList.setLayoutManager(flexboxLayoutManager);

        TagAdapter tagAdapter = new TagAdapter(tagList, PlaylistDetailsActivity.this);
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