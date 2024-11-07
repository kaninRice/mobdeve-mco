package com.example.s17.escopete.stevenerrol.arpeggeo.playlist.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s17.escopete.stevenerrol.arpeggeo.R;
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.PlaylistRepositoryImpl;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.Tag;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.TagRepositoryImpl;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.TextColor;

import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    private final TagRepositoryImpl tagRepositoryImpl;
    private final PlaylistRepositoryImpl playlistRepositoryImpl;

    private final Context context;

    public PlaylistAdapter(TagRepositoryImpl tagRepositoryImpl, PlaylistRepositoryImpl playlistRepositoryImpl, PlaylistListActivity activity) {
        this.tagRepositoryImpl = tagRepositoryImpl;
        this.playlistRepositoryImpl = playlistRepositoryImpl;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.partial_playlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String playlistName = playlistRepositoryImpl.getPlaylistNameByIndex(position);

        holder.itemView.setTag("isNotSelected");
        holder.playlistNameView.setText(playlistName);
        holder.playlistImageView.setImageResource(playlistRepositoryImpl.getPlaylistImage(playlistName));

        populateTagsContainer(playlistName, holder);

        /* Highlight list item */
        holder.itemView.setOnLongClickListener(view -> {
            String tag = (String) holder.itemView.getTag();

            if (!tag.equals("isSelected")) {
                holder.itemView.setTag("isSelected");
                holder.playlistContainerView.setBackground(
                        new ColorDrawable(ContextCompat.getColor(context, R.color.dark_layer_2))
                );
            } else {
                holder.itemView.setTag("isNotSelected");
                holder.playlistContainerView.setBackground(
                        new ColorDrawable(ContextCompat.getColor(context, R.color.dark_layer_1))
                );
            }

            return true;
        });
    }

    private void populateTagContainer(ArrayList<Tag> tagList, LayoutInflater layoutInflater, ViewHolder holder) {
        int tagCounter = 0;

        for (int i = 0; i < tagList.size(); i++) {
            final String tagName = tagRepositoryImpl.getTagNameByIndex(i);

            View view = layoutInflater.inflate(R.layout.partial_tag, null);
            CardView cv = view.findViewById(R.id.tag_card);
            TextView tv = view.findViewById(R.id.tag_text);

            /* Truncate tags if number exceeds 3 */
            if (tagCounter == 3) {
                tv.setText("...");
                holder.tagsContainerView.addView(view);
                break;
            }

            cv.setTag(tagName);
            cv.setCardBackgroundColor(Color.parseColor(tagRepositoryImpl.getTagColor(tagName)));

            tv.setText(tagName);

            if (tagRepositoryImpl.getTagTextColor(tagName) == TextColor.LIGHT) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.light_gray));
            } else {
                tv.setTextColor(ContextCompat.getColor(context, R.color.dark_layer_1));
            }

            holder.tagsContainerView.addView(view);
            tagCounter++;
        }
    }

    private void populateTagsContainer(String playlistName, ViewHolder holder) {
        // TODO: check possible improvement to choosing which tag to display
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if (!playlistRepositoryImpl.getPlaylistTagList(playlistName).isEmpty()) {
            populateTagContainer(playlistRepositoryImpl.getPlaylistTagList(playlistName),
                    layoutInflater, holder);
        } else {
            /* show "no tags" indication */
            layoutInflater.inflate(R.layout.partial_tag, holder.tagsContainerView, true);
        }

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, PlaylistDetailsActivity.class);
            intent.putExtra("playlistName", playlistName);
            context.startActivity(intent);
        });
    }

    public int getItemCount() {
        return playlistRepositoryImpl.getAllPlaylists().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout playlistContainerView;
        ImageView playlistImageView;
        TextView playlistNameView;
        LinearLayout tagsContainerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playlistContainerView = itemView.findViewById(R.id.playlist_container);
            playlistImageView = itemView.findViewById(R.id.playlist_image);
            playlistNameView = itemView.findViewById(R.id.playlist_name);
            tagsContainerView = itemView.findViewById(R.id.tags_container);
        }
    }
}
