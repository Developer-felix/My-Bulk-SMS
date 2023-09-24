package com.example.mybulksms.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybulksms.R;
import com.example.mybulksms.domains.Group;

import java.util.List;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ViewHolder> {

    private List<Group> groupList;

    public GroupListAdapter(List<Group> groupList) {
        this.groupList = groupList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Group group = groupList.get(position);
        holder.groupNameTextView.setText(group.getName());
        holder.groupDescriptionTextView.setText(group.getDescription());
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView groupNameTextView;
        TextView groupDescriptionTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            groupNameTextView = itemView.findViewById(R.id.groupNameTextView);
            groupDescriptionTextView = itemView.findViewById(R.id.groupDescriptionTextView);
        }
    }
}
