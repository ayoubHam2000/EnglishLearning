package com.example.englishlearning.Services;

import android.content.Context;
import android.widget.Toast;

public class Methods {



    public static void makeMessage(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void makeMessage(Context context, int resources){
        Toast.makeText(context, resources, Toast.LENGTH_SHORT).show();
    }

}
