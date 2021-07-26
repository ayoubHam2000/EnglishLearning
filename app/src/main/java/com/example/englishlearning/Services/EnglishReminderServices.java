package com.example.englishlearning.Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.englishlearning.Modles.Branches;
import com.example.englishlearning.Modles.EnglishReminderStruct;
import com.example.englishlearning.Utilities.Const;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.englishlearning.Utilities.Const.S_SHARE_WORD_LIST;
import static com.example.englishlearning.Utilities.Const.S_WORD_ADDED;

public class EnglishReminderServices {

    public static List<Branches> list = new ArrayList<>();
    public static Branches selectedBranch = null;
    public static EnglishReminderStruct selectedItem = null;
    public static boolean DisplayActive = true;
    public static boolean isLoaded = false; // first the info is loaded it will be true in intro fragment

    private static boolean successGetComplete = false;

    /*public static void addToList(EnglishReminderStruct newItem, Context context){
        list.add(newItem);
        saveList(context);
    }*/

    public static void saveList(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(S_SHARE_WORD_LIST, json);
        editor.apply();
        Methods.makeMessage(context, S_WORD_ADDED);
        System.out.println(S_WORD_ADDED);
    }

    public static void loadList(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = pref.getString(S_SHARE_WORD_LIST, null);
        Type type = new TypeToken<ArrayList<Branches>>(){}.getType();
        final List<Branches> loadedList = gson.fromJson(json, type);

        if(loadedList != null){
            list = loadedList;
            System.out.println("audios restore");
        }else{
            System.out.println("audios not restore or the list is empty");
        }
    }


    //get the Branches List with its Vocabularies
    public static void getList(Context context, final Complete complete){
        String url = Const.URL_BRANCH;

        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>(){

            @Override
            public void onResponse(JSONArray response) {
                successGetComplete = true;
                getAllBranches(response); //if failed successGetComplete = false;
                complete.onComplete(successGetComplete);
            }

        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                complete.onComplete(false);
                Log.d("Failed", error.toString());
            }
        });
        Volley.newRequestQueue(context).add(jsonRequest);
    }

    private static void getAllBranches(JSONArray response){
        list.clear();
        try {
            int x;
            for(x = 0; x < response.length(); x++){
                JSONObject branch = response.getJSONObject(x);
                list.add(getBranch(branch));

            }
        } catch (JSONException e) {
            successGetComplete = false;
            e.printStackTrace();
        }
    }

    private static Branches getBranch(JSONObject jsonBranch){
        String name;
        JSONArray jsonVocabularies;
        Branches newBranch = null;

        try {
            name = jsonBranch.getString("Branch");
            jsonVocabularies = jsonBranch.getJSONArray("list");
            newBranch = new Branches(name, getBranchVocabulary(jsonVocabularies));
        } catch (JSONException e) {
            successGetComplete = false;
            e.printStackTrace();
        }

        return newBranch;
    }

    private static List<EnglishReminderStruct> getBranchVocabulary(JSONArray jsonVocabularies){
        int y;
        List<EnglishReminderStruct> newVocabularyList = new ArrayList<>();

        try {
            for(y = 0; y < jsonVocabularies.length(); y++){
                JSONObject theWord = jsonVocabularies.getJSONObject(y);
                String word = theWord.getString("word");
                String meaning = theWord.getString("meaning");
                String examples = theWord.getString("examples");
                String other = theWord.getString("other");

                EnglishReminderStruct newElement = new EnglishReminderStruct(
                        word,
                        meaning,
                        examples,
                        other,
                        1
                );

                newVocabularyList.add(newElement);

            }
        } catch (JSONException e) {
            successGetComplete = false;
            e.printStackTrace();
        }
        return newVocabularyList;
    }


    //----------------------------------------------------------------
    public interface Complete{
        void onComplete(boolean complete);
    }

    /*public static boolean isVocabularyNotExist(String word){
        int i;
        int j;
        for(i = 0; i < list.size(); i++){
            List<EnglishReminderStruct> vocabulary = list.get(i).list;
            if(vocabulary != null){
                for(j = 0; j < vocabulary.size(); j++){
                    if(word.equals(vocabulary.get(j).word))
                        return false;
                }
            }

        }
        return true;
    }*/


    public static void deleteItemByName(String name){
        int i;
        List<EnglishReminderStruct> vocabularyList = selectedBranch.list;
        for(i = 0; i < vocabularyList.size(); i++){
            if(name.equals(vocabularyList.get(i).word)){
                vocabularyList.remove(i);
                break;
            }
        }
    }
}
