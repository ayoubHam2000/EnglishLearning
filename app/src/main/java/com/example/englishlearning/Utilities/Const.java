package com.example.englishlearning.Utilities;

import android.content.Context;

public final class Const {

    private Const(){

    }

    public static final int NOT_RECORDING = 0;
    public static final int RECORDING = 1;
    public static final int NOT_PLAYING = 0;
    public static final int PLAYING = 1;
    public static final int PAUSED = 2;

    //list Item
    public static final int DELETE_ITEM = 0;
    public static final int SET_ACTIVE = 1;
    public static final int GET_ITEM = 2;

    private static final String URL = "http://192.168.1.10:3000/";
    public static final String URL_List = URL + "list";
    public static final String URL_BRANCH = URL + "branches";

    //string

    //----> ListAudioAdapter
    public static final String S_RECORDING = "Recording";
    public static final String S_STOPPED = "Stop";
    public static final String S_PAUSE = "PAUSE";
    public static final String S_RECORD_ADDED = "the new record added";
    public static final String S_EMPTY_TEXT = "Text field is empty";
    public static final String S_NOT_AUDIO = "No Audio Recording";

    //colors
    public static final String C_RED = "379CDE";


    //---> AudioListServices
    public static final String S_SHARE_LIST = "listOfRecord";

    //---> EnglishReminderServices
    public static final String S_SHARE_WORD_LIST = "listOfWords";
    public static final String S_WORD_ADDED = "word saved";

    //---> EnglishReminder
    public static final String S_FEILD_WORD_EMPTY       = "Word field is empty";
    public static final String S_FEILD_Meaning_EMPTY    = "Meaning field is empty";

    //---> Intro
    public static final String S_LIST_EMPTY = "list audio is empty";

    //--->Solution
    public static final String S_TEXT_NOT_FOUND = "no Item Selected";

    //---> EnglishReminderListDisplay
    public static String setColorText(String str, String color){
        return "<font color='#"+ color + "'>" + str + "</font>";
    }

    //quiz
    public static String getColorText(String str){
        String color = "EE0000";
        return "<solid><font color='#"+ color + "'>" + str + "</font></solid>";
    }

    //---> var
    public static String getMainPath(Context context){
        return context.getExternalFilesDir("/").getAbsolutePath();
    }
}
