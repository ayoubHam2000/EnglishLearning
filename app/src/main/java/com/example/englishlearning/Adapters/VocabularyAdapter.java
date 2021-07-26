package com.example.englishlearning.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishlearning.Modles.EnglishReminderStruct;
import com.example.englishlearning.R;

import java.util.List;

public class VocabularyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<EnglishReminderStruct> listItem;
    Context context;

    public VocabularyAdapter(Context context, List<EnglishReminderStruct> listItem){
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vocabulary, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public int getItemCount() {
        return listItem.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).onBindView(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView word;
        private TextView meaning;
        private TextView examples;
        private TextView other;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            word = itemView.findViewById(R.id.vocabularyName);
            meaning = itemView.findViewById(R.id.vocabularyMeaning);
            examples = itemView.findViewById(R.id.vocabularyExamples);
            other = itemView.findViewById(R.id.vocabularyOther);
        }

        private void onBindView(int position){
            word.setText(listItem.get(position).word);
            meaning.setText(listItem.get(position).meaning);
            if(!listItem.get(position).examples.equals(".")){
                examples.setText(listItem.get(position).examples);
            }

            if(!listItem.get(position).other.equals(".")){
                other.setText(listItem.get(position).other);
            }

        }

    }

}
