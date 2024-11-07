package com.example.s17.escopete.stevenerrol.arpeggeo.playlist.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.s17.escopete.stevenerrol.arpeggeo.R;
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.PlaylistRepositoryImpl;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.Tag;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.TagRepositoryImpl;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.TextColor;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PlaylistPreviewDialog extends BottomSheetDialogFragment {
    @Inject
    TagRepositoryImpl tagRepositoryImpl;

    @Inject
    PlaylistRepositoryImpl playlistRepositoryImpl;

    ImageView playlistImage;
    TextView playlistNameView;
    TextView playlistUrl;
    FlexboxLayout tagsContainer;
    AppCompatButton buttonEdit;
    AppCompatButton buttonDelete;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_playlist_preview, container, false);


        getDialog().getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        String playlistName = (String) getArguments().get("playlistName");

        bindViews(v);
        updateViews(v, playlistName);

        buttonEdit.setOnClickListener(this::editEntry);
        buttonDelete.setOnClickListener(this::deleteEntry);

        return v;
    }

    private void bindViews(View v) {
        playlistImage = v.findViewById(R.id.playlist_image);
        playlistNameView = v.findViewById(R.id.playlist_name);
        playlistUrl = v.findViewById(R.id.playlist_url);
        tagsContainer = v.findViewById(R.id.tags_container);
        buttonEdit = v.findViewById(R.id.button_edit);
        buttonDelete = v.findViewById(R.id.button_delete);
    }

    private void updateViews(View v, String playlistName) {
        playlistImage.setImageResource(playlistRepositoryImpl.getPlaylistImage(playlistName));
        playlistNameView.setText(playlistName);
        playlistUrl.setText(Html.fromHtml(
                getString(R.string.open_in_spotify, playlistRepositoryImpl.getPlaylistUrl(playlistName)),
                Html.FROM_HTML_MODE_LEGACY));
        playlistUrl.setMovementMethod(LinkMovementMethod.getInstance()); /* Set text as clickable */

        LayoutInflater layoutInflater = LayoutInflater.from(v.getContext());
        if (!playlistRepositoryImpl.getPlaylistTagList(playlistName).isEmpty()) {
            ArrayList<Tag> tagList = playlistRepositoryImpl.getPlaylistTagList(playlistName);

            for (int i = 0; i < tagList.size(); i++) {
                String tagName = tagRepositoryImpl.getTagNameByIndex(i);

                View view = layoutInflater.inflate(R.layout.partial_tag, null);
                CardView cardView = view.findViewById(R.id.tag_card);
                TextView textView = view.findViewById(R.id.tag_text);

                cardView.setTag(tagName);
                cardView.setCardBackgroundColor(Color.parseColor(tagRepositoryImpl.getTagColor(tagName)));

                textView.setText(tagName);

                if (tagRepositoryImpl.getTagTextColor(tagName) == TextColor.LIGHT) {
                    textView.setTextColor(ContextCompat.getColor(v.getContext(), R.color.light_gray));
                } else {
                    textView.setTextColor(ContextCompat.getColor(v.getContext(), R.color.dark_layer_1));
                }

                tagsContainer.addView(view);
            }

        } else {
            /* show no tags indication */
            layoutInflater.inflate(R.layout.partial_tag, tagsContainer, true);
        }
    }

    public void editEntry(View v) {
        dismiss();
    }

    public void deleteEntry(View v) {
        dismiss();
    }
}