package com.invizorys.mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.invizorys.mobile.R;
import com.invizorys.mobile.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Paryshkura Roman on 13.12.2015.
 */
public class ParticipantRecyclerAdapter extends RecyclerView.Adapter<ParticipantRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<User> participants;

    public ParticipantRecyclerAdapter(Context context, ArrayList<User> participants) {
        this.context = context;
        this.participants = participants;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_participant_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User participant = getItem(position);
        Picasso.with(context).load(participant.getAvatarUrl()).into(holder.imageViewAvatar);
        String name = participant.getFirstName() + " " + participant.getLastName();
        holder.textViewName.setText(name);
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    public User getItem(int position) {
        return participants.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewAvatar;
        public TextView textViewName;

        public ViewHolder(View view) {
            super(view);
            imageViewAvatar = (ImageView) view.findViewById(R.id.imageview_avatar);
            textViewName = (TextView) view.findViewById(R.id.textview_name);
        }
    }

}
