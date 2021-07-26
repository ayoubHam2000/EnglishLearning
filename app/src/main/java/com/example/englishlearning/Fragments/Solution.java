package com.example.englishlearning.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.englishlearning.Modles.EnglishText;
import com.example.englishlearning.R;
import com.example.englishlearning.Services.AudioListServices;
import com.example.englishlearning.Services.Methods;

import static com.example.englishlearning.Utilities.Const.S_TEXT_NOT_FOUND;

/**
 * A simple {@link Fragment} subclass.
 */
public class Solution extends Fragment {


    //view
    private TextView solutionText = null;

    //var
    private EnglishText englishText = null;

    public Solution() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_solution, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initVar();
        setView();
    }

    private void initView(View view){
        solutionText = view.findViewById(R.id.solutionText);
    }

    private void initVar(){
        englishText = AudioListServices.theSelectedItem;
    }

    private void setView(){
        if(englishText != null){
            solutionText.setText(englishText.text);
        }else{
            Methods.makeMessage(getContext(), S_TEXT_NOT_FOUND);
        }
    }
}
