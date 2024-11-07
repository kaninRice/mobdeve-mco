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
import com.example.s17.escopete.stevenerrol.arpeggeo.playlist.ui.PlaylistDetailsActivity;
import com.example.s17.escopete.stevenerrol.arpeggeo.R;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.TagRepositoryImpl;
import com.example.s17.escopete.stevenerrol.arpeggeo.tag.data.TextColor;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {
    TagRepositoryImpl tagRepositoryImpl;
    PlaylistRepositoryImpl playlistRepositoryImpl;

    private final Context context;

    public TagAdapter(TagRepositoryImpl tagRepositoryImpl, PlaylistRepositoryImpl playlistRepositoryImpl, PlaylistDetailsActivity activity) {
        this.tagRepositoryImpl = tagRepositoryImpl;
        this.playlistRepositoryImpl = playlistRepositoryImpl;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.partial_tag, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagAdapter.ViewHolder holder, int position) {
        final String tagName = tagRepositoryImpl.getTagNameByIndex(position);

        holder.tagCardView.setCardBackgroundColor(Color.parseColor(tagRepositoryImpl.getTagColor(tagName)));
        holder.tagTextView.setText(tagName);

        if (tagRepositoryImpl.getTagTextColor(tagName) == TextColor.LIGHT) {
            holder.tagTextView.setTextColor(ContextCompat.getColor(context, R.color.light_gray));
        } else {
            holder.tagTextView.setTextColor(ContextCompat.getColor(context, R.color.dark_layer_1));
        }
    }

    @Override
    public int getItemCount() {
        return tagRepositoryImpl.getAllTags().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView tagCardView;
        TextView tagTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagCardView = itemView.findViewById(R.id.tag_card);
            tagTextView = itemView.findViewById(R.id.tag_text);
        }
    }
}
