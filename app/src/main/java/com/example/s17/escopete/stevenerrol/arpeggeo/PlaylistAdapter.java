package com.example.s17.escopete.stevenerrol.arpeggeo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

        holder.itemView.setTag("isNotSelected");
        holder.playlistName.setText(playlist.getName());
        holder.playlistImage.setImageResource(playlist.getImage());

        // Populate tags (max: 3)
        // TODO: check possible improvement to choosing which tag to display
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if (!playlist.getTagList().isEmpty()) {
            ArrayList<Tag> tagList = playlist.getTagList();
            int tagCounter = 0;

            for (Tag tag : tagList) {
                View view = layoutInflater.inflate(R.layout.partial_tag, null);
                CardView cv = view.findViewById(R.id.tag_card);
                TextView tv = view.findViewById(R.id.tag_text);

                // Truncate tags if number exceeds 3
                if (tagCounter == 3) {
                    tv.setText("...");
                    holder.tagsContainer.addView(view);
                    break;
                }

                cv.setTag(tag.getName());
                cv.setCardBackgroundColor(Color.parseColor(tag.getColor()));

                tv.setText(tag.getName());

                if (tag.getTextColor() == TextColor.LIGHT) {
                    tv.setTextColor(ContextCompat.getColor(context, R.color.light_gray));
                } else {
                    tv.setTextColor(ContextCompat.getColor(context, R.color.dark_layer_1));
                }

                holder.tagsContainer.addView(view);
                tagCounter++;
            }

        } else {
            /* show no tags indication */
            layoutInflater.inflate(R.layout.partial_tag, holder.tagsContainer, true);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlaylistDetailsActivity.class);

                intent.putExtra("playlist", playlist);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("tagList", playlist.getTagList());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                /* Highlight list item */
                String tag = (String) holder.itemView.getTag();

                if (!tag.equals("isSelected")) {
                    holder.itemView.setTag("isSelected");
                    holder.playlistContainer.setBackground(
                            new ColorDrawable(ContextCompat.getColor(context, R.color.dark_layer_2))
                    );
                } else {
                    holder.itemView.setTag("isNotSelected");
                    holder.playlistContainer.setBackground(
                            new ColorDrawable(ContextCompat.getColor(context, R.color.dark_layer_1))
                    );
                }

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return playlistList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout playlistContainer;
        ImageView playlistImage;
        TextView playlistName;
        LinearLayout tagsContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playlistContainer = itemView.findViewById(R.id.playlist_container);
            playlistImage = itemView.findViewById(R.id.playlist_image);
            playlistName = itemView.findViewById(R.id.playlist_name);
            tagsContainer = itemView.findViewById(R.id.tags_container);
        }
    }
}
