package com.example.englishlearning.Classes;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;
import android.widget.SeekBar;

import androidx.core.app.ActivityCompat;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class MediaAudio  {

    private Context context;
    private Activity activity;
    private String mainPath;

    public MediaAudio(Context context, Activity activity, String mainPath){
        this.context = context;
        this.activity = activity;
        this.mainPath = mainPath;
    }

    //const
    private final int NOT_RECORDING = 0;
    private final int RECORDING = 1;
    private final int NOT_PLAYING = 0;
    private final int PLAYING = 1;
    private final int PAUSED = 2;

    public int statusRecord = NOT_RECORDING;    // 0: notRecording, 1:Recording, 2:Paused
    public int statusPlay   = NOT_PLAYING;      // 0: notPlaying, 1:Playing, 2:Paused

    private String recordedFileName = null;
    private String playedFileName = null;
    public MediaPlayer mediaPlayer;
    private MediaRecorder mediaRecorder;

    public void playAudio(File file){
        mediaPlayer = new MediaPlayer();

        if(statusPlay == NOT_PLAYING){
            try {
                mediaPlayer.setDataSource(mainPath + file.getAbsolutePath());
                mediaPlayer.prepare();
                mediaPlayer.start();
                statusPlay = PLAYING;
                playedFileName = file.getName();
                Log.d("STATUS", "playing audio : " + file.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void pauseAudio(){
        if(statusPlay == PLAYING){
            statusPlay = PAUSED;
            mediaPlayer.pause();
            Log.d("STATUS", "pauseAudio audio : " + playedFileName);
        }
    }

    public void resumePlaying(){
        if(statusPlay == PAUSED){
            statusPlay = PLAYING;
            mediaPlayer.start();
            Log.d("STATUS", "resumePlaying audio : " + playedFileName);
        }
    }

    public void stopPlaying(){
        if(statusPlay == PLAYING){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            statusPlay = NOT_PLAYING;
            Log.d("STATUS", "stopPlaying audio : " + playedFileName);
        }
    }

    public int getDuration(){
        return mediaPlayer.getDuration();
    }

    public int getCurrentPosition(){
        return mediaPlayer.getCurrentPosition();
    }

    //--------------------------------------------
    //record

    public void recordAudio(String fileName){
        if(mainPath != null && audioPermission() && statusRecord == NOT_RECORDING){
            Date now = new Date();
            String dateInSecond = String.valueOf(now.getTime());
            String name = mainPath + "/" + fileName + "_" + dateInSecond + ".m4a";
            mediaRecorder = new MediaRecorder();

            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setOutputFile(name);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mediaRecorder.setAudioChannels(1);
            mediaRecorder.setAudioSamplingRate(44100);
            mediaRecorder.setAudioEncodingBitRate(96000);

            try {
                mediaRecorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaRecorder.start();
            statusRecord = RECORDING;
            recordedFileName = fileName + "_" + dateInSecond + ".m4a";
            Log.d("STATUS", "recording audio : " + recordedFileName);
        }else{
            if(mainPath == null){
                Log.d("STATUS", "mainPath is null");
            }else{
                Log.d("STATUS", "permission not granted or wrong mainPath");
            }
        }
    }

    public void stopRecording(){
        if(statusRecord != NOT_RECORDING){
            statusRecord = NOT_RECORDING;
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            Log.d("STATUS", "stopRecording audio : " + recordedFileName);
        }
    }

    public void pauseRecording(){
        if(statusRecord == RECORDING){
            statusRecord = PAUSED;
            mediaRecorder.pause();
            Log.d("STATUS", "pauseRecording audio : " + recordedFileName);
        }
    }

    public void resumeRecording(){
        if(statusRecord == PAUSED){
            statusRecord = RECORDING;
            mediaRecorder.resume();
            Log.d("STATUS", "resumeRecording audio : " + recordedFileName);
        }
    }

    public void discardRecoding(){
        if(statusRecord != NOT_RECORDING){
            stopRecording();
            deleteFile(recordedFileName);
            statusRecord = NOT_RECORDING;
        }
    }


    //---------------------- audio permission
    private boolean audioPermission(){
        String permission = Manifest.permission.RECORD_AUDIO;
        int recordPermissionCode = 21; //randomValue

        if(ActivityCompat.checkSelfPermission(context, permission) == 0){
            return true;
        }else{
            ActivityCompat.requestPermissions(activity, new String[]{permission}, recordPermissionCode);
            Log.d("PERMISSION", "not Granted");
            return false;
        }
    }


    public File getPlayedFile(){
        return new File(mainPath + "/" + playedFileName);
    }

    public File getRecordedFile(){
        return new File(mainPath + "/" + recordedFileName);
    }

    public void deleteFile(String name){
        File theFile = new File(mainPath + "/" + name);
        theFile.delete();
        Log.d("STATUS", "file Deleted : " + name);
    }



}
