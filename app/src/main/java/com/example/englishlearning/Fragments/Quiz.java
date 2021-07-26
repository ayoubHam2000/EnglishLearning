package com.example.englishlearning.Fragments;


import android.gesture.Gesture;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.englishlearning.Adapters.TextErrors;
import com.example.englishlearning.Classes.MediaAudio;
import com.example.englishlearning.Modles.EnglishText;
import com.example.englishlearning.R;
import com.example.englishlearning.Services.AudioListServices;
import com.example.englishlearning.Utilities.Const;

/**
 * A simple {@link Fragment} subclass.
 */
public class Quiz extends Fragment implements View.OnClickListener{


    public Quiz() {
        // Required empty public constructor
    }

    //view
    private EditText        quizTextFeild       = null;
    private Button          quizTestBtn         = null;
    private SeekBar         quizProgressBar     = null;
    private ImageButton     quizItemPauseBtn    = null;
    private ImageButton     quizItemStopBtn     = null;
    private ImageView       displayCorrection   = null;
    private NavController   navController       = null;
    private CheckBox        showSolutionInBox   = null;

    //var
    private MediaAudio      mediaAudio          = null;
    private String          mainPath            = null;
    private EnglishText     englishText         = null;
    private Handler         seekBarHandler      = null;
    private Runnable        seekBarRunnable     = null;
    private Boolean         isTraking           = false;
    private TextErrors      textErrors          = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initVar();
        initButtons();

    }

    private void initView(View view){
        navController = Navigation.findNavController(view);
        quizTextFeild = view.findViewById(R.id.quizTextFeild);
        quizTestBtn = view.findViewById(R.id.quizTestBtn);
        quizProgressBar = view.findViewById(R.id.quizProgressBar);
        quizItemPauseBtn = view.findViewById(R.id.quizItemPauseBtn);
        quizItemStopBtn = view.findViewById(R.id.quizItemStopBtn);
        displayCorrection = view.findViewById(R.id.displayCorrection);
        showSolutionInBox = view.findViewById(R.id.showSolutionInBox);

    }

    private void initVar(){
        mainPath = Const.getMainPath(getContext());
        mediaAudio = new MediaAudio(getContext(), getActivity(), mainPath);

        englishText = AudioListServices.theSelectedItem;
        textErrors = new TextErrors(getContext());
    }

    private void initButtons(){
        quizItemPauseBtn.setOnClickListener(this);
        quizItemStopBtn.setOnClickListener(this);
        quizTestBtn.setOnClickListener(this);
        displayCorrection.setOnClickListener(this);
    }



    //------------------------------------------
    //buttons

    private void btnItemPauseBtn(){
        //itemPauseBtn
        if(mediaAudio.statusPlay == Const.NOT_PLAYING){
            mediaAudio.playAudio(englishText.file);
            quizItemPauseBtn.setImageResource(R.drawable.list_pause_btn);
            updateTheSeekBar();
            mediaFinish();
            onSeekerChange();
        }else if(mediaAudio.statusPlay == Const.PAUSED){
            mediaAudio.resumePlaying();
            quizItemPauseBtn.setImageResource(R.drawable.list_pause_btn);
        }else{
            mediaAudio.pauseAudio();
            quizItemPauseBtn.setImageResource(R.drawable.list_play_btn);
        }
    }

    //method used by btnItemPauseBtn();
    private void mediaFinish(){
        mediaAudio.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btnItemStopBtnClick();
            }
        });
    }

    private void updateTheSeekBar(){
        quizProgressBar.setMax(mediaAudio.getDuration());
        seekBarHandler = new Handler();
        seekBarRunnable = new Runnable() {
            @Override
            public void run() {
                if(mediaAudio.statusPlay != Const.NOT_PLAYING){
                    if(!isTraking)
                        quizProgressBar.setProgress(mediaAudio.getCurrentPosition());
                    seekBarHandler.postDelayed(this, 700);
                }

            }
        };
        seekBarHandler.postDelayed(seekBarRunnable, 0);
    }

    private void onSeekerChange(){
        quizProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isTraking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(mediaAudio.mediaPlayer != null)
                    mediaAudio.mediaPlayer.seekTo(seekBar.getProgress());
                else
                    seekBar.setProgress(0);
                isTraking = false;
            }
        });
    }

    //------------ second button

    private void btnItemStopBtnClick(){
        System.out.println("stop");
        if(mediaAudio.statusPlay != Const.NOT_PLAYING){
            mediaAudio.stopPlaying();
            quizItemPauseBtn.setImageResource(R.drawable.list_play_btn);
            quizProgressBar.setProgress(0);
        }
    }

    //--------------- third button

    private void btnQuizTestBtn(){
        //quizTestBtn
        String[] rightAnswer = englishText.text.split(" ");
        String[] myAnswer = quizTextFeild.getText().toString().split(" ");
        int i = 0;
        StringBuilder result = new StringBuilder();
        while(i < rightAnswer.length && i < myAnswer.length){
            if(rightAnswer[i].equals(myAnswer[i].toLowerCase())){
                result.append(myAnswer[i]);
            }else{
                result.append(Const.getColorText(myAnswer[i]));
            }
            result.append(" ");
            i++;
        }
        if(i < rightAnswer.length){
            result.append(" ...");
        }else if(rightAnswer.length != myAnswer.length){
            result.append(" <<<");
        }else{
            result.append(Const.getColorText(" ."));
        }
        if(showSolutionInBox.isChecked()){
            result.append("<br><br>------------------------------------<br><br>");
            result.append(englishText.text);
        }

        textErrors.creat(result.toString());
    }


    private void btnDisplayCorrection(){
        navController.navigate(R.id.action_quiz_to_solution);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.quizItemPauseBtn:
                btnItemPauseBtn();
                break;
            case R.id.quizItemStopBtn:
                btnItemStopBtnClick();
                break;
            case R.id.quizTestBtn:
                btnQuizTestBtn();
                break;
            case R.id.displayCorrection:
                btnDisplayCorrection();
                break;
        }
    }

    @Override
    public void onPause() {
        if(mediaAudio.statusPlay == Const.PLAYING){
            btnItemPauseBtn();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        btnItemStopBtnClick();
        super.onDestroy();
    }


}
