package com.example.englishlearning.Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;

import androidx.preference.PreferenceManager;

import com.example.englishlearning.Modles.EnglishText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.englishlearning.Utilities.Const.S_SHARE_LIST;

public class AudioListServices {

    public static List<EnglishText> recordList = new ArrayList<>();
    public static EnglishText theSelectedItem = null;
    public static Boolean quiz = false;


    public static void saveRecordList(Context context){
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(recordList);
        editor.putString(S_SHARE_LIST, json);
        editor.apply();
        System.out.println("audio saved");
    }

    public static void restoreRecordList(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(S_SHARE_LIST, null);
        Type type = new TypeToken<ArrayList<EnglishText>>() {}.getType();
        final List<EnglishText> loadedList = gson.fromJson(json, type);

        if(loadedList != null){
            recordList = loadedList;
            System.out.println("audios restore");
        }else{
            System.out.println("audios not restore or the list is empty");
        }
    }

}
