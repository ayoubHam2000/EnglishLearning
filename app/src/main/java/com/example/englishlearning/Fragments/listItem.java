package com.example.englishlearning.Fragments;


import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
public class listItem extends Fragment implements View.OnClickListener {


    public listItem() { }

    //view
    private TextView    itemTextView    = null;
    private SeekBar     progressBar     = null;
    private ImageButton itemPauseBtn    = null;
    private ImageButton itemStopBtn     = null;

    //var
    private MediaAudio mediaAudio = null;
    private String mainPath = null;
    private EnglishText englishText = null;
    private Handler seekBarHandler;
    private Runnable seekBarRunnable;
    private Boolean isTraking = false;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initVar();
        initButtons();

        //set

        setViewInfo();
    }

    private void initView(View view){
        itemTextView = view.findViewById(R.id.itemTextView);
        progressBar = view.findViewById(R.id.progressBar);
        itemPauseBtn = view.findViewById(R.id.itemPauseBtn);
        itemStopBtn = view.findViewById(R.id.itemStopBtn);

    }

    private void initVar(){
        mainPath = Const.getMainPath(getContext());
        mediaAudio = new MediaAudio(getContext(), getActivity(), mainPath);
        englishText = AudioListServices.theSelectedItem;
    }

    private void initButtons(){
        itemPauseBtn.setOnClickListener(this);
        itemStopBtn.setOnClickListener(this);
    }

    //----------------------------------------
    //view
    private void setViewInfo(){
        itemTextView.setText(englishText.text);
    }





    //------------------------------------------
    //buttons

    private void btnItemPauseBtn(){
        //itemPauseBtn
        if(mediaAudio.statusPlay == Const.NOT_PLAYING){
            mediaAudio.playAudio(englishText.file);
            itemPauseBtn.setImageResource(R.drawable.list_pause_btn);
            updateTheSeekBar();
            mediaFinish();
            onSeekerChange();
        }else if(mediaAudio.statusPlay == Const.PAUSED){
            mediaAudio.resumePlaying();
            itemPauseBtn.setImageResource(R.drawable.list_pause_btn);
        }else{
            mediaAudio.pauseAudio();
            itemPauseBtn.setImageResource(R.drawable.list_play_btn);
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
        progressBar.setMax(mediaAudio.getDuration());
        seekBarHandler = new Handler();
        seekBarRunnable = new Runnable() {
            @Override
            public void run() {
                if(mediaAudio.statusPlay != Const.NOT_PLAYING){
                    if(!isTraking)
                        progressBar.setProgress(mediaAudio.getCurrentPosition());
                    seekBarHandler.postDelayed(this, 700);
                }

            }
        };
        seekBarHandler.postDelayed(seekBarRunnable, 0);
    }

    private void onSeekerChange(){
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
            itemPauseBtn.setImageResource(R.drawable.list_play_btn);
            progressBar.setProgress(0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.itemPauseBtn:
                btnItemPauseBtn();
                break;
            case R.id.itemStopBtn:
                btnItemStopBtnClick();
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
