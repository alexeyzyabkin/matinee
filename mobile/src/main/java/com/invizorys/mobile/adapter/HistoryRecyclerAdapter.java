package com.invizorys.mobile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.invizorys.mobile.R;
import com.invizorys.mobile.model.HistoryRecord;
import com.invizorys.mobile.util.Utils;

import java.util.List;

/**
 * Created by Paryshkura Roman on 05.01.2016.
 */
public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder> {
    private List<HistoryRecord> historyRecords;

    public HistoryRecyclerAdapter(List<HistoryRecord> historyRecords) {
        this.historyRecords = historyRecords;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HistoryRecord historyRecord = historyRecords.get(position);
        holder.textviewDate.setText(Utils.dateToString(historyRecord.getTaskDone()));
        holder.textviewParticipantName.setText(historyRecord.getUserName());
        holder.textviewTaskTitle.setText(historyRecord.getTaskName());
    }

    @Override
    public int getItemCount() {
        return historyRecords.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textviewDate, textviewParticipantName, textviewTaskTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            textviewDate = (TextView) itemView.findViewById(R.id.textview_date);
            textviewParticipantName = (TextView) itemView.findViewById(R.id.textview_participant_name);
            textviewTaskTitle = (TextView) itemView.findViewById(R.id.textview_task_title);
        }
    }
}
