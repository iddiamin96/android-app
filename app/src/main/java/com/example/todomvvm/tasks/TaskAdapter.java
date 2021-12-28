package com.example.todomvvm.tasks;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todomvvm.R;
import com.example.todomvvm.database.TaskEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private static final String DATE_FORMAT = "dd/MM/yyy";

    final private ItemClickListener mItemClickListener;
    private List<TaskEntry> mTaskEntries;
    private Context mContext;
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    public TaskAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.task_layout, parent, false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        TaskEntry taskEntry = mTaskEntries.get(position);
        String title = taskEntry.getTitle();
        String description = taskEntry.getDescription();
        int priority = taskEntry.getPriority();
        String updatedAt = dateFormat.format(taskEntry.getUpdatedAt());

        holder.taskTitleView.setText(title);
        holder.taskDescriptionView.setText(description);
        holder.updatedAtView.setText(updatedAt);

        String priorityString = "" + priority;
        holder.priorityView.setText(priorityString);

        GradientDrawable priorityCircle = (GradientDrawable) holder.priorityView.getBackground();
        int priorityColor = getPriorityColor(priority);
        priorityCircle.setColor(priorityColor);
    }


    private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch (priority) {
            case 1:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
                break;
            case 2:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialOrange);
                break;
            case 3:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                break;
            default:
                break;
        }
        return priorityColor;
    }

    @Override
    public int getItemCount() {
        if (mTaskEntries == null) {
            return 0;
        }
        return mTaskEntries.size();
    }

    public List<TaskEntry> getTasks(){
        return mTaskEntries;
    }

    public void setTasks(List<TaskEntry> taskEntries) {
        mTaskEntries = taskEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView taskTitleView;
        TextView taskDescriptionView;
        TextView updatedAtView;
        TextView priorityView;

        public TaskViewHolder(View itemView) {
            super(itemView);

            taskTitleView = itemView.findViewById(R.id.taskTitle);
            taskDescriptionView = itemView.findViewById(R.id.taskDescription3);
            updatedAtView = itemView.findViewById(R.id.taskUpdatedAt);
            priorityView = itemView.findViewById(R.id.priorityTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = mTaskEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}
