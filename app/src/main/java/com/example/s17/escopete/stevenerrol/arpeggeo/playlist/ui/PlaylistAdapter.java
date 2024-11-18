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
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.ui.TagAdapter;

import java.util.ArrayList;

/**
 * Adapter for displaying {@link com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.Playlist}s
 * in a {@link RecyclerView}
 */
public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    private final TagRepositoryImpl tagRepositoryImpl;
    private final PlaylistRepositoryImpl playlistRepositoryImpl;

    private final Context context;

    /**
     * Creates an instance of {@link PlaylistAdapter}
     * @param tagRepositoryImpl The {@link TagRepositoryImpl} which provides tag data
     * @param playlistRepositoryImpl The {@link PlaylistRepositoryImpl} which provides playlist data
     * @param activity The activity which provides context
     */
    public PlaylistAdapter(TagRepositoryImpl tagRepositoryImpl, PlaylistRepositoryImpl playlistRepositoryImpl, PlaylistListActivity activity) {
        this.tagRepositoryImpl = tagRepositoryImpl;
        this.playlistRepositoryImpl = playlistRepositoryImpl;
        this.context = activity;
    }

    /**
     * Creates a view holder for a playlist item
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     * @return A {@link ViewHolder}
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.partial_playlist, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Sets data in the view holder
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String playlistName = playlistRepositoryImpl.getPlaylistNameByIndex(position);

        holder.itemView.setTag("isNotSelected");
        holder.playlistNameView.setText(playlistName);
        holder.playlistImageView.setImageResource(playlistRepositoryImpl.getPlaylistImage(playlistName));

        populateTagsContainer(playlistName, holder);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, PlaylistDetailsActivity.class);
            intent.putExtra("playlistName", playlistName);
            context.startActivity(intent);
        });

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

    /**
     * Populate the tag container with tags
     * @param playlistName The name of the {@link com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.Playlist}
     * @param holder The {@link ViewHolder} which has the tag container
     */
    private void populateTagsContainer(String playlistName, ViewHolder holder) {
        // TODO: check possible improvement to choosing which tag to display
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if (!playlistRepositoryImpl.getPlaylistTagList(playlistName).isEmpty()) {
            int tagCounter = 0;

            for (int i = 0; i < tagRepositoryImpl.getAllTags().size(); i++) {
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
        } else {
            /* show "no tags" indication */
            layoutInflater.inflate(R.layout.partial_tag, holder.tagsContainerView, true);
        }
    }

    /**
     * Retrieves the number of items to put in the {@link RecyclerView}
     * @return The number of items to put in the {@link RecyclerView} in {@code int}
     */
    public int getItemCount() {
        return playlistRepositoryImpl.getAllPlaylists().size();
    }

    /**
     * View holder for displaying a playlist in a {@link RecyclerView}
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout playlistContainerView;
        ImageView playlistImageView;
        TextView playlistNameView;
        LinearLayout tagsContainerView;

        /**
         * Creates an instance of {@link TagAdapter.ViewHolder}
         * @param itemView The view of the item
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playlistContainerView = itemView.findViewById(R.id.playlist_container);
            playlistImageView = itemView.findViewById(R.id.playlist_image);
            playlistNameView = itemView.findViewById(R.id.playlist_name);
            tagsContainerView = itemView.findViewById(R.id.tags_container);
        }
    }
}
