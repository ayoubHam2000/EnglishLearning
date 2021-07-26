package com.example.englishlearning.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishlearning.Modles.EnglishText;
import com.example.englishlearning.R;

import java.io.File;
import java.util.List;

public class ListAudioAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<EnglishText> list;
    private onItemListClick itemClick;

    public ListAudioAdapter(List<EnglishText> list, onItemListClick itemClick){
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_english_text, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).onBindView(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView fileName;


        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            fileName = itemView.findViewById(R.id.fileName);

        }

        private void onBindView(final int position){
            fileName.setText(list.get(position).text);
            fileName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fileClicked(position);
                }
            });
        }

        private void fileClicked(int position){
            System.out.println(list.get(position).file);
            itemClick.onClickListener(list.get(position));
        }

    }

    public interface onItemListClick{
        void onClickListener(EnglishText englishTextItem);
    }
}
