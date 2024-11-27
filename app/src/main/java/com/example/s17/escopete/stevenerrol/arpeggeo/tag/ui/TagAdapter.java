package com.example.s17.escopete.stevenerrol.arpeggeo.tag.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.data.PlaylistRepositoryImpl;
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.ui.PlaylistAdapter;
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.ui.PlaylistDetailsActivity;
import com.example.s17.escopete.stevenerrol.arpeggeo.R;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.Tag;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.TagRepositoryImpl;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.TextColor;

/**
 * Adapter for displaying {@link com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.Tag}s
 * in a {@link RecyclerView}
 */
public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {
    TagRepositoryImpl tagRepositoryImpl;
    PlaylistRepositoryImpl playlistRepositoryImpl;

    private final Context context;
    private final long playlistId;

    /**
     * Creates an instance of {@link TagAdapter}
     * @param tagRepositoryImpl The {@link TagRepositoryImpl} which provides tag data
     * @param playlistRepositoryImpl The {@link PlaylistRepositoryImpl} which provides playlist data
     * @param activity The activity which provides context
     */
    public TagAdapter(long playlistId, TagRepositoryImpl tagRepositoryImpl, PlaylistRepositoryImpl playlistRepositoryImpl, PlaylistDetailsActivity activity) {
        this.playlistId = playlistId;
        this.tagRepositoryImpl = tagRepositoryImpl;
        this.playlistRepositoryImpl = playlistRepositoryImpl;
        this.context = activity;
    }

    /**
     * Creates a view holder for a tag item
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     * @return A {@link PlaylistAdapter.ViewHolder}
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.partial_tag, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Sets data in the view holder
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull TagAdapter.ViewHolder holder, int position) {
        final Tag tag = tagRepositoryImpl.getTagInAllTagsWithPlaylistIdByIndex(playlistId, position);
        final String tagName = tag.getName();

        holder.tagCardView.setCardBackgroundColor(Color.parseColor(tagRepositoryImpl.getTagColor(tagName)));
        holder.tagTextView.setText(tagName);

        if (tagRepositoryImpl.getTagTextColor(tagName) == TextColor.LIGHT) {
            holder.tagTextView.setTextColor(ContextCompat.getColor(context, R.color.light_gray));
        } else {
            holder.tagTextView.setTextColor(ContextCompat.getColor(context, R.color.dark_layer_1));
        }
    }

    /**
     * Retrieves the number of items to put in the {@link RecyclerView}
     * @return The number of items to put in the {@link RecyclerView} in {@code int}
     */
    @Override
    public int getItemCount() {
        return tagRepositoryImpl.getAllTagsWithPlaylistId(playlistId).size();
    }

    /**
     * View holder for displaying a tag in a {@link RecyclerView}
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView tagCardView;
        TextView tagTextView;

        /**
         * Creates an instance of {@link ViewHolder}
         * @param itemView The view of the item
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagCardView = itemView.findViewById(R.id.tag_card);
            tagTextView = itemView.findViewById(R.id.tag_text);
        }
    }
}
