package com.example.englishlearning.Modles;

import java.util.List;

public class Branches {

    public String name;
    public List<EnglishReminderStruct> list;

    public Branches(String name, List<EnglishReminderStruct> list){
        this.name = name;
        this.list = list;
    }
}
