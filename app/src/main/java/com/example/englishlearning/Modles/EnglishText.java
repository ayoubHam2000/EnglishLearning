package com.example.englishlearning.Modles;

import java.io.File;

public class EnglishText {

    public String text;
    public File file;

    public EnglishText(File file, String text){
        this.text = text;
        this.file = file;
    }
}
