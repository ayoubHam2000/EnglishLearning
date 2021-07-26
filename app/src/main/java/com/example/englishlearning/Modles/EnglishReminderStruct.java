package com.example.englishlearning.Modles;

public class EnglishReminderStruct {

    public String word;
    public String meaning;
    public String examples;
    public String other;
    public int isActive;

    public EnglishReminderStruct(String word, String meaning, String examples, String other, int isActive){
        this.word = word;
        this.meaning = meaning;
        this.examples = examples;
        this.other = other;
        this.isActive = isActive;
    }
}
