package com.example.englishlearning.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishlearning.Modles.Branches;
import com.example.englishlearning.R;
import com.example.englishlearning.Services.EnglishReminderServices;

import java.util.List;

public class BranchesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Branches> listItem;
    private onItemClick onClickListener;


    public BranchesAdapter(List<Branches> listItem, onItemClick onClickListener){
        this.listItem = listItem;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_english_reminder, parent, false);
        return new viewHolder(view);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((viewHolder)(holder)).onBindView(position);
    }

    class viewHolder extends RecyclerView.ViewHolder{

        private final TextView branchName;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            branchName = itemView.findViewById(R.id.branchName);
        }

        private void onBindView(final int position){
            branchName.setText(listItem.get(position).name);
            branchName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemTextReminderClick(position);
                }
            });

        }

        private void itemTextReminderClick(int position){
            EnglishReminderServices.selectedBranch = listItem.get(position);
            onClickListener.onClickListener();
        }

    }

    public interface onItemClick{
        void onClickListener();
    }

}
