package com.example.englishlearning.Fragments.pEnglishReminder;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.englishlearning.Adapters.VocabularyAdapter;
import com.example.englishlearning.Modles.EnglishReminderStruct;
import com.example.englishlearning.R;
import com.example.englishlearning.Services.EnglishReminderServices;
import com.example.englishlearning.Utilities.Const;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnglishReminderListDisplay extends Fragment {

    //view
    private RecyclerView vocabulariesList;

    //var
    private VocabularyAdapter vocabularyAdapter;

    public EnglishReminderListDisplay() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.english_reminder_display_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        setView();
    }

    private void initView(View view){
        vocabulariesList = view.findViewById(R.id.vocabulariesList);
    }

    private void setView(){

        initEnglishReminderView();
    }

    //---------------------------------------
    //set View

    private void initEnglishReminderView(){
        vocabularyAdapter = new VocabularyAdapter(getContext(), EnglishReminderServices.selectedBranch.list);

        vocabulariesList.setAdapter(vocabularyAdapter);
        vocabulariesList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
