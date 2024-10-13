package com.example.s17.escopete.stevenerrol.arpeggeo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    ArrayList<Playlist> playlistList;
    Context context;

    public PlaylistAdapter(ArrayList<Playlist> playlistList, PlaylistListActivity activity) {
        this.playlistList = playlistList;
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
        final Playlist playlist = playlistList.get(position);

        holder.playlistName.setText(playlist.getName());
        holder.playlistImage.setImageResource(playlist.getImage());

        // Populate tags (max: 3)
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if (!playlist.getTagList().isEmpty()) {
            ArrayList<Tag> tagList = playlist.getTagList();
            int tagCounter = 0;

            for (Tag tag : tagList) {
                View view = layoutInflater.inflate(R.layout.partial_tag, null);
                CardView cv = view.findViewById(R.id.tagCard);
                TextView tv = view.findViewById(R.id.tagText);

                // Truncate tags if number exceeds 3
                if (tagCounter == 3) {
                    tv.setText("...");
                    holder.tagsContainer.addView(view);
                    break;
                }

                cv.setTag(tag.getName());
                cv.setCardBackgroundColor(Color.parseColor(tag.getColor()));

                tv.setText(tag.getName());
                tv.setTextColor(Color.parseColor("#FFFFFF"));

                holder.tagsContainer.addView(view);
                tagCounter++;
            }

        } else {
            layoutInflater.inflate(R.layout.partial_tag, holder.tagsContainer, true);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Call Playlist Details Activity
            }
        });
    }

    @Override
    public int getItemCount() {
        return playlistList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView playlistImage;
        TextView playlistName;
        LinearLayout tagsContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playlistImage = itemView.findViewById(R.id.playlistImage);
            playlistName = itemView.findViewById(R.id.playlistName);
            tagsContainer = itemView.findViewById(R.id.tagsContainer);
        }
    }
}
