package com.invizorys.mobile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.invizorys.mobile.R;
import com.invizorys.mobile.model.realm.Task;

import java.util.ArrayList;

/**
 * Created by Paryshkura Roman on 20.12.2015.
 */
public class MyTaskRecyclerAdapter extends RecyclerView.Adapter<MyTaskRecyclerAdapter.ViewHolder> {
    private ArrayList<Task> tasks;

    public MyTaskRecyclerAdapter(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public MyTaskRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyTaskRecyclerAdapter.ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.textViewTaskName.setText(task.getName());
        holder.textViewTaskDescription.setText(task.getDescription());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTaskName;
        public TextView textViewTaskDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewTaskName = (TextView) itemView.findViewById(R.id.textview_task_name);
            textViewTaskDescription = (TextView) itemView.findViewById(R.id.textview_task_description);
        }
    }
}
