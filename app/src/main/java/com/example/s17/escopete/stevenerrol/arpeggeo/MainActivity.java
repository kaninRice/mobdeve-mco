package com.example.s17.escopete.stevenerrol.arpeggeo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Playlist> playlistList;
    private ArrayList<Tag> tagList;

    Button temp_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeApp();

        temp_button = findViewById(R.id.temp_button);
    }

    public void initializeApp() {
        initializeTags();
        initializePlaylist();
    }

    public void initializeTags() {
        tagList = new ArrayList<>();

        tagList.add(
                new Tag("teka lang", "#04A793")
        );

        tagList.add(
                new Tag("asdf", "#D035A5")
        );
    }

    public void initializePlaylist() {
        playlistList = new ArrayList<>();

        playlistList.add(
                new Playlist(
                        "https://open.spotify.com/playlist/6P20B2kzD3G25bQYJ6HSPl",
                        "bakit",
                        R.drawable.ic_default_playlist_image,
                        new ArrayList<>(Arrays.asList(
                                new Tag("Tag 1", "#BB86FC"),
                                new Tag("Tag 2", "#0DFFFFFF"),
                                new Tag("Tag 3", "#6D6D6D"),
                                new Tag("Tag 4", "#FFFFFF")
                        ))
                )
        );

        playlistList.add(
                new Playlist(
                        "https://open.spotify.com/playlist/6P20B2kzD3G25bQYJ6HSPl",
                        "itchy nadal",
                        R.drawable.image_playlist_cover_1,
                        new ArrayList<>(Arrays.asList(
                                new Tag("Tag 1", "#BB86FC"),
                                new Tag("Tag 2", "#0DFFFFFF")
                        ))
                )
        );

        playlistList.add(
                new Playlist(
                        "https://open.spotify.com/playlist/6P20B2kzD3G25bQYJ6HSPl",
                        "Byaheng UV Express",
                        R.drawable.image_playlist_cover_1,
                        tagList
                                .stream()
                                .filter(tag ->
                                        tag.getName().equals("teka lang"))
                                .collect(Collectors.toCollection(ArrayList::new))
                )
        );

        playlistList.add(
                new Playlist(
                        "https://open.spotify.com/playlist/6P20B2kzD3G25bQYJ6HSPl",
                        "teka lang",
                        R.drawable.image_playlist_cover_2,
                        new ArrayList<>()
                )
        );
    }

    public void temp_start_activity(View v) {
        Intent intent = new Intent(MainActivity.this, PlaylistListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("playlistList", playlistList);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}