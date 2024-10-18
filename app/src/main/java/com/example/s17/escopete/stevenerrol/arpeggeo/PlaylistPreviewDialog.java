package com.example.s17.escopete.stevenerrol.arpeggeo;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class PlaylistPreviewDialog extends BottomSheetDialogFragment {
    ArrayList<Tag> tagList;

    ImageView playlistImage;
    TextView playlistName;
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
        Playlist playlist = (Playlist) getArguments().get("playlist");

        playlistImage = v.findViewById(R.id.playlist_image);
        playlistName = v.findViewById(R.id.playlist_name);
        playlistUrl = v.findViewById(R.id.playlist_url);
        tagsContainer = v.findViewById(R.id.tags_container);
        buttonEdit = v.findViewById(R.id.button_edit);
        buttonDelete = v.findViewById(R.id.button_delete);

        playlistImage.setImageResource(playlist.getImage());
        playlistName.setText(playlist.getName());
        playlistUrl.setText(Html.fromHtml(
                getString(R.string.open_in_spotify, playlist.getUrl()),
                Html.FROM_HTML_MODE_LEGACY));
        playlistUrl.setMovementMethod(LinkMovementMethod.getInstance());

        LayoutInflater layoutInflater = LayoutInflater.from(v.getContext());
        if (!playlist.getTagList().isEmpty()) {
            ArrayList<Tag> tagList = playlist.getTagList();

            for (Tag tag : tagList) {
                View view = layoutInflater.inflate(R.layout.partial_tag, null);
                CardView cv = view.findViewById(R.id.tag_card);
                TextView tv = view.findViewById(R.id.tag_text);

                cv.setTag(tag.getName());
                cv.setCardBackgroundColor(Color.parseColor(tag.getColor()));

                tv.setText(tag.getName());

                if (tag.getTextColor() == TextColor.LIGHT) {
                    tv.setTextColor(ContextCompat.getColor(v.getContext(), R.color.light_gray));
                } else {
                    tv.setTextColor(ContextCompat.getColor(v.getContext(), R.color.dark_layer_1));
                }

                tagsContainer.addView(view);
            }

        } else {
            /* show no tags indication */
            layoutInflater.inflate(R.layout.partial_tag, tagsContainer, true);
        }

        return v;
    }
}