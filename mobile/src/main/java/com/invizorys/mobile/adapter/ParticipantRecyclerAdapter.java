package com.invizorys.mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.invizorys.mobile.R;
import com.invizorys.mobile.model.Participant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Paryshkura Roman on 13.12.2015.
 */
public class ParticipantRecyclerAdapter extends RecyclerView.Adapter<ParticipantRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Participant> participants;

    public ParticipantRecyclerAdapter(Context context, ArrayList<Participant> participants) {
        this.context = context;
        this.participants = participants;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_participant, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Participant participant = getItem(position);
        Picasso.with(context).load(participant.getUser().getAvatarUrl()).into(holder.imageViewAvatar);
        String name = participant.getUser().getName() + " " + participant.getUser().getSurname();
        holder.textViewName.setText(name);

        if (participant.getRole() == null) {
            holder.imageViewArrow.setVisibility(View.VISIBLE);
            holder.textViewRole.setVisibility(View.GONE);
        } else {
            holder.imageViewArrow.setVisibility(View.INVISIBLE);
            holder.textViewRole.setVisibility(View.VISIBLE);
            holder.textViewRole.setText(participant.getRole().getName());
        }
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    public Participant getItem(int position) {
        return participants.get(position);
    }

    public void updateParticipants(ArrayList<Participant> participants) {
        this.participants = participants;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewAvatar;
        public TextView textViewName;
        public TextView textViewRole;
        public ImageView imageViewArrow;

        public ViewHolder(View view) {
            super(view);
            imageViewAvatar = (ImageView) view.findViewById(R.id.imageview_avatar);
            textViewName = (TextView) view.findViewById(R.id.textview_name);
            textViewRole = (TextView) view.findViewById(R.id.textview_role);
            imageViewArrow = (ImageView) view.findViewById(R.id.imageview_arrow);
        }
    }

}
