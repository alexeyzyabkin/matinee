package com.invizorys.mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.invizorys.mobile.R;
import com.invizorys.mobile.model.realm.Event;
import com.invizorys.mobile.util.Utils;

import java.util.List;

/**
 * Created by Paryshkura Roman on 30.12.2015.
 */
public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder> {
    private List<Event> events;
    private Context context;
    private EventListener eventListener;

    public EventRecyclerAdapter(List<Event> events, EventListener eventListener) {
        this.events = events;
        this.eventListener = eventListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Event event = events.get(position);
        holder.textViewEventTitle.setText(event.getName());
        holder.textViewParticipantsNumber.setText(String.valueOf(event.getParticipants().size()));
        holder.textViewData.setText(Utils.dateToString(event.getStartDate()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventListener.onSelected(event);
                Toast.makeText(context, "selected event id = " + event.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewEventTitle;
        public TextView textViewParticipantsNumber;
        public TextView textViewData;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewEventTitle = (TextView) itemView.findViewById(R.id.textview_event_title);
            textViewParticipantsNumber = (TextView) itemView.findViewById(R.id.textview_participants_number);
            textViewData = (TextView) itemView.findViewById(R.id.textview_data);
        }
    }

    public interface EventListener {
        void onSelected(Event event);
    }
}
