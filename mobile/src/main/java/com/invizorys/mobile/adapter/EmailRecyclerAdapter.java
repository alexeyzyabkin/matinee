package com.invizorys.mobile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.invizorys.mobile.R;

import java.util.ArrayList;

/**
 * Created by Paryshkura Roman on 20.12.2015.
 */
public class EmailRecyclerAdapter extends RecyclerView.Adapter<EmailRecyclerAdapter.ViewHolder> {
    private ArrayList<String> emails;

    public EmailRecyclerAdapter(ArrayList<String> emails) {
        this.emails = emails;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_email, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String email = emails.get(position);
        holder.textViewEmail.setText(email);
    }

    @Override
    public int getItemCount() {
        return emails.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewEmail;
        public ImageView imageViewDeleteEmail;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewEmail = (TextView) itemView.findViewById(R.id.textview_email);
            imageViewDeleteEmail = (ImageView) itemView.findViewById(R.id.imageview_delete_email);
        }
    }
}
