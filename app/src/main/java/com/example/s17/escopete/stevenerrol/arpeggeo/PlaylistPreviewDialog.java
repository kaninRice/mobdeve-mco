package com.example.s17.escopete.stevenerrol.arpeggeo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsetsController;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class PlaylistPreviewDialog extends BottomSheetDialogFragment {
    ArrayList<Tag> tagList;

    ImageView playlistImage;
    TextView playlistName;
    TextView playlistUrl;
    RecyclerView recyclerTagList;
    AppCompatButton buttonEdit;
    AppCompatButton buttonDelete;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_playlist_preview, container, false);

        playlistImage = v.findViewById(R.id.playlistImage);
        playlistName = v.findViewById(R.id.playlistName);
        playlistUrl = v.findViewById(R.id.playlistUrl);
        recyclerTagList = v.findViewById(R.id.recyclerTagList);
        buttonEdit = v.findViewById(R.id.buttonEdit);
        buttonDelete = v.findViewById(R.id.buttonDelete);

        return v;
    }
}