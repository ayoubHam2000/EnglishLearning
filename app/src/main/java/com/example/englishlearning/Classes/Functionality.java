package com.example.englishlearning.Classes;

import android.content.Context;
import android.widget.Toast;

public class Functionality {

    Context context;

    public Functionality(Context context){
        this.context = context;
    }

    public void makeMessage(String message){
        Toast newToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        newToast.show();
    }

    public void makeMessage(int resources){
        Toast newToast = Toast.makeText(context, resources, Toast.LENGTH_SHORT);
        newToast.show();
    }
}
