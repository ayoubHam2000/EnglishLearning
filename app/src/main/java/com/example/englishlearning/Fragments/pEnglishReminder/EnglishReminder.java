package com.example.englishlearning.Fragments.pEnglishReminder;


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
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.englishlearning.Modles.EnglishReminderStruct;
import com.example.englishlearning.R;
import com.example.englishlearning.Services.EnglishReminderServices;
import com.example.englishlearning.Services.Methods;
import com.example.englishlearning.Utilities.Const;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnglishReminder extends Fragment implements View.OnClickListener{

    //view
    private EditText        wordReminder;
    private EditText        wordMeaning;
    private EditText        wordExamples;
    private EditText        wordOther;
    private Button          wordButton;
    private Button          toWordListBtn;
    private NavController   navController;
    private CheckBox displayActive;


    public EnglishReminder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.english_reminder_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initVar();
        initBtn();
    }


    private void initView(View view){
        wordReminder  = view.findViewById(R.id.wordReminder);
        wordMeaning = view.findViewById(R.id.wordMening);
        wordExamples = view.findViewById(R.id.wordExamples);
        wordOther = view.findViewById(R.id.wordOther);
        wordButton = view.findViewById(R.id.wordButton);
        toWordListBtn = view.findViewById(R.id.toWordListBtn);
        navController = Navigation.findNavController(view);
        displayActive = view.findViewById(R.id.displayActive);
    }

    private void initVar(){

    }

    private void initBtn(){
        wordButton.setOnClickListener(this);
        toWordListBtn.setOnClickListener(this);
        displayActive.setOnClickListener(this);
    }




    //----------------------------------------------
    //buttons

    private void wordButtonClick(){
        final String word = wordReminder.getText().toString();
        final String meaning = wordMeaning.getText().toString();
        final String examples = wordExamples.getText().toString();
        final String other = wordOther.getText().toString();
        final EnglishReminderStruct newWord = new EnglishReminderStruct(word, meaning, examples, other, 1);

        if(word.isEmpty() || meaning.isEmpty()){
            if(word.isEmpty())
                Methods.makeMessage(getContext(), Const.S_FEILD_WORD_EMPTY);
            if(meaning.isEmpty())
                Methods.makeMessage(getContext(), Const.S_FEILD_Meaning_EMPTY);
        }
        /*else if(EnglishReminderServices.isVocabularyNotExist(word)){
            //EnglishReminderServices.addToList(newWord, getContext());
            wordReminder.setText("");
            wordMeaning.setText("");
            wordExamples.setText("");
            wordOther.setText("");
        }else{
            Methods.makeMessage(getContext(), "the word exist");
        }*/
    }

    private void toWordListBtnClick(){
        navController.navigate(R.id.action_englishReminder_to_englishReminderBranches);
    }

    private void displayActiveClick(){
        EnglishReminderServices.DisplayActive = displayActive.isChecked();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wordButton:
                wordButtonClick();
                break;
            case R.id.toWordListBtn:
                toWordListBtnClick();
                break;
            case R.id.displayActive:
                displayActiveClick();
                break;
        }
    }
}
