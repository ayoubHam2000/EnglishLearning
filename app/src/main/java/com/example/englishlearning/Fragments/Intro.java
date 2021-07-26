package com.example.englishlearning.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.englishlearning.Modles.EnglishText;
import com.example.englishlearning.R;
import com.example.englishlearning.Services.AudioListServices;
import com.example.englishlearning.Services.EnglishReminderServices;
import com.example.englishlearning.Services.Methods;
import com.example.englishlearning.Utilities.Const;

import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class Intro extends Fragment implements View.OnClickListener {

    private Button toRecordFragment = null;
    private Button toListFragment = null;
    private NavController navigation = null;
    private Button toQuizBtn = null;
    private Button toRandomQuiz = null;
    private Button toWordsBtn = null;

    public Intro() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!EnglishReminderServices.isLoaded){
            loadSavedVar();
            EnglishReminderServices.isLoaded = true;
        }
        initView(view);
        initButtons();
    }

    private void loadSavedVar(){
        AudioListServices.restoreRecordList(getContext());
        EnglishReminderServices.loadList(getContext());
        EnglishReminderServices.getList(getContext(), new EnglishReminderServices.Complete() {
            @Override
            public void onComplete(boolean complete) {
                if(complete){
                    System.out.println("get success");
                    Methods.makeMessage(getContext(), "success to get list");
                    EnglishReminderServices.saveList(getContext());
                }else{
                    System.out.println("get not success");
                    Methods.makeMessage(getContext(), "Failed get list from server");
                }

            }
        });
    }

    private void initView(View view){
        toRecordFragment = view.findViewById(R.id.toRecordFragment);
        toListFragment = view.findViewById(R.id.toListFragment);
        navigation = Navigation.findNavController(view);
        toQuizBtn = view.findViewById(R.id.toQuizBtn);
        toRandomQuiz = view.findViewById(R.id.toRandomQuiz);
        toWordsBtn = view.findViewById(R.id.toWordsBtn);
    }

    private void initButtons(){
        toRecordFragment.setOnClickListener(this);
        toListFragment.setOnClickListener(this);
        toQuizBtn.setOnClickListener(this);
        toRandomQuiz.setOnClickListener(this);
        toWordsBtn.setOnClickListener(this);
    }


    //-------------------------------------------
    //buttons

    private void btnToRecordFragment(){
        //toRecordFragment
        navigation.navigate(R.id.action_intro_to_recordFragment);

    }

    private void btnToListFragment(){
        //toListFragment
        navigation.navigate(R.id.action_intro_to_informationList);
    }

    private void toQuizFragment(){
        //toQuizBtn
        AudioListServices.quiz = true;
        navigation.navigate(R.id.action_intro_to_informationList);
    }

    private void toRandomQuizFragment(){
        //toRandomQuiz
        if(AudioListServices.recordList.size() > 0){
            Random newRandom = new Random();
            int randomValue = newRandom.nextInt(AudioListServices.recordList.size());
            AudioListServices.theSelectedItem = AudioListServices.recordList.get(randomValue);
            navigation.navigate(R.id.action_intro_to_quiz);
        }else{
            Methods.makeMessage(getContext(), Const.S_LIST_EMPTY);
        }

    }

    private void toWordsBtnClick(){
        navigation.navigate(R.id.action_intro_to_englishReminderBranches);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toRecordFragment :
                btnToRecordFragment();
                break;
            case R.id.toListFragment :
                btnToListFragment();
                break;
            case R.id.toQuizBtn:
                toQuizFragment();
                break;
            case R.id.toRandomQuiz:
                toRandomQuizFragment();
                break;
            case R.id.toWordsBtn:
                toWordsBtnClick();
                break;
        }
    }
}
