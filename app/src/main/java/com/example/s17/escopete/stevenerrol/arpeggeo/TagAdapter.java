package com.example.s17.escopete.stevenerrol.arpeggeo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {
    ArrayList<Tag> tagList;
    Context context;

    public TagAdapter(ArrayList<Tag> tagList, PlaylistDetailsActivity activity) {
        this.tagList = tagList;
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
        final Tag tag = tagList.get(position);

        holder.tagCard.setCardBackgroundColor(Color.parseColor(tag.getColor()));
        holder.tagText.setText(tag.getName());
        holder.tagText.setTextColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView tagCard;
        TextView tagText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagCard = itemView.findViewById(R.id.tagCard);
            tagText = itemView.findViewById(R.id.tagText);
        }
    }
}
