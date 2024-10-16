package com.example.s17.escopete.stevenerrol.arpeggeo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlaylistDetailsActivity extends AppCompatActivity {
    ArrayList<Tag> tagList;

    LinearLayout activityHeader;
    ImageView playlistImage;
    TextView playlistName;
    TextView playlistUrl;
    RecyclerView recyclerTagList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_details);

        /* adjust padding based on top system bars */
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activityLayout), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(v.getPaddingStart(), systemBars.top + v.getPaddingTop(), v.getPaddingEnd(), v.getPaddingBottom());
                return insets;
            }
        );

        initializeActivity();
    }

    public void initializeActivity() {
        activityHeader = findViewById(R.id.activityHeader);
        playlistImage = findViewById(R.id.playlistImage);
        playlistName = findViewById(R.id.playlistName);
        playlistUrl = findViewById(R.id.playlistUrl);
        recyclerTagList = findViewById(R.id.recyclerTagList);

        Intent intent = getIntent();
        Playlist playlist = (Playlist) intent.getParcelableExtra("playlist");

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerTagList.setHasFixedSize(true);

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerTagList.setLayoutManager(linearLayoutManager);

        TagAdapter tagAdapter = new TagAdapter(tagList, PlaylistDetailsActivity.this);
        recyclerTagList.setAdapter(tagAdapter);
    }

    public void closeActivity(View v) {
        finish();
    }
}