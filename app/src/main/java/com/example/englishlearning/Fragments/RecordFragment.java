package com.example.englishlearning.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.englishlearning.Classes.MediaAudio;
import com.example.englishlearning.Modles.EnglishText;
import com.example.englishlearning.R;
import com.example.englishlearning.Services.AudioListServices;
import com.example.englishlearning.Services.Methods;
import com.example.englishlearning.Utilities.Const;

import java.io.File;

import static com.example.englishlearning.Utilities.Const.S_PAUSE;
import static com.example.englishlearning.Utilities.Const.S_RECORDING;
import static com.example.englishlearning.Utilities.Const.S_STOPPED;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordFragment extends Fragment implements View.OnClickListener {

    //view
    private ImageButton buttonRecord;
    private ImageButton buttonDiscardRecord;
    private ImageButton buttonStopRecording;
    private Button buttonSaveAudio;
    private Chronometer timer;
    private EditText    editText;
    private TextView    recordStatus;

    //var
    private MediaAudio mediaAudio;
    private String recordedFile = null;
    private String mainPath = null;
    private Long timeWhenStopped = 0L;

    //const



    public RecordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initVar();
        initButtons();
    }

    private void initView(View view){
        buttonRecord = view.findViewById(R.id.buttonRecord);
        buttonDiscardRecord = view.findViewById(R.id.buttonDiscardRecord);
        buttonStopRecording = view.findViewById(R.id.buttonStopRecording);
        timer = view.findViewById(R.id.timerRecord);
        editText = view.findViewById(R.id.textFeild);
        recordStatus = view.findViewById(R.id.recordStatus);
        buttonSaveAudio = view.findViewById(R.id.buttonSaveAudio);
    }

    private void initVar(){
        mainPath = Const.getMainPath(getContext());
        mediaAudio = new MediaAudio(getContext(), getActivity(), mainPath);
    }

    private void initButtons(){
        buttonRecord.setOnClickListener(this);
        buttonDiscardRecord.setOnClickListener(this);
        buttonStopRecording.setOnClickListener(this);
        buttonSaveAudio.setOnClickListener(this);
    }

    //---------------------------------------
    //buttons
    private void btnRecordClick(){
        //buttonRecord
        if(mediaAudio.statusRecord == Const.RECORDING){
            pauseRecording();
        }else if(mediaAudio.statusRecord == Const.NOT_RECORDING){
            mediaAudio.recordAudio("R_");
            if(mediaAudio.statusRecord == Const.RECORDING){
                recordStatus.setText(S_RECORDING);
                buttonRecord.setImageResource(R.drawable.record_btn_recording);
                startChronometer();
                //set visible
                buttonStopRecording.setVisibility(View.VISIBLE);
                buttonDiscardRecord.setVisibility(View.VISIBLE);
                //delete previous record when we try to record the second time
                if(recordedFile != null){
                    mediaAudio.deleteFile(recordedFile);
                    recordedFile = null;
                }
            }
        }else if(mediaAudio.statusRecord == Const.PAUSED){
            mediaAudio.resumeRecording();
            recordStatus.setText(S_RECORDING);
            buttonRecord.setImageResource(R.drawable.record_btn_recording);
            resumeChronometer();
        }
    }

    private void pauseRecording(){
        mediaAudio.pauseRecording();
        recordStatus.setText(S_PAUSE);
        buttonRecord.setImageResource(R.drawable.record_btn_stopped);
        pauseChronometer();
    }

    //---
    private void btnStopRecording(){
        //buttonStopRecording
        if(mediaAudio.statusRecord != Const.NOT_RECORDING){
            mediaAudio.stopRecording();
            recordStatus.setText(S_STOPPED);
            buttonRecord.setImageResource(R.drawable.record_btn_stopped);
            stopChronometer();
            //set invisible
            buttonStopRecording.setVisibility(View.INVISIBLE);
            buttonDiscardRecord.setVisibility(View.INVISIBLE);
            //get recorded file name
            recordedFile = mediaAudio.getRecordedFile().getName();
        }
    }

    private void btnDiscardRecordClick(){
        //buttonDiscardRecord
        if(mediaAudio.statusRecord != Const.NOT_RECORDING){
            mediaAudio.discardRecoding();
            recordStatus.setText(S_STOPPED);
            buttonRecord.setImageResource(R.drawable.record_btn_stopped);
            stopChronometer();
            //set visible
            buttonStopRecording.setVisibility(View.INVISIBLE);
            buttonDiscardRecord.setVisibility(View.INVISIBLE);
        }
    }

    private void btnSaveClick(){
        //buttonSaveAudio
        if(recordedFile != null){
            if(editText.getText().length() != 0){
                //save the new element in android
                String theText = editText.getText().toString();
                EnglishText newEnglishRecord = new EnglishText(new File(recordedFile), theText);
                AudioListServices.recordList.add(newEnglishRecord);
                AudioListServices.saveRecordList(getContext());

                //restore to default
                recordedFile = null;
                editText.setText("");
                Methods.makeMessage(getContext(), Const.S_RECORD_ADDED);
            }else{
                Methods.makeMessage(getContext(), Const.S_EMPTY_TEXT);
            }
        }else{
            Methods.makeMessage(getContext(), Const.S_NOT_AUDIO);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonRecord:
                btnRecordClick();
                break;
            case R.id.buttonDiscardRecord:
                btnDiscardRecordClick();
                break;
            case R.id.buttonStopRecording:
                btnStopRecording();
                break;
            case R.id.buttonSaveAudio:
                btnSaveClick();
                break;
        }
    }

    //------------------------
    //methods

    private void startChronometer(){
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();
    }

    private void pauseChronometer(){
        timeWhenStopped = timer.getBase() - SystemClock.elapsedRealtime();
        timer.stop();
    }

    private void resumeChronometer(){
        timer.setBase(timeWhenStopped + SystemClock.elapsedRealtime());
        timer.start();
    }

    private void stopChronometer(){
        timer.setBase(SystemClock.elapsedRealtime());
        timer.stop();
    }



    @Override
    public void onPause() {
        if(mediaAudio.statusRecord == Const.RECORDING)
            pauseRecording();
        super.onPause();
    }

    @Override
    public void onDetach() {
        btnDiscardRecordClick();
        super.onDetach();
    }
}
