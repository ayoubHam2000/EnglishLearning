package com.example.englishlearning.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.englishlearning.Adapters.ListAudioAdapter;
import com.example.englishlearning.Modles.EnglishText;
import com.example.englishlearning.R;
import com.example.englishlearning.Services.AudioListServices;
import com.example.englishlearning.Utilities.Const;

import java.util.List;
import java.util.function.ToDoubleBiFunction;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformationList extends Fragment implements ListAudioAdapter.onItemListClick {


    //view
    private RecyclerView listRecyclerView;
    private NavController navController;

    //vars
    private ListAudioAdapter listAudioAdapter;
    private List<EnglishText> listText;
    private String mainPath;

    public InformationList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_information_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initVar();
        initFunction();
    }

    //-------------------------------
    //init

    private void initView(View view){
        listRecyclerView = view.findViewById(R.id.listAudio);
        navController = Navigation.findNavController(view);
    }

    private void initVar(){
        mainPath = Const.getMainPath(getContext());
        listText = AudioListServices.recordList;

    }

    private void initFunction(){

        initRecyclerView();
    }

    //-----------------------------------
    //....//

    private void initRecyclerView() {

        listAudioAdapter = new ListAudioAdapter(listText, this);

        listRecyclerView.setAdapter(listAudioAdapter);
        listRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    //when item clicked in adapter
    @Override
    public void onClickListener(EnglishText englishTextItem) {
        AudioListServices.theSelectedItem = englishTextItem;
        System.out.println(AudioListServices.theSelectedItem.text);
        if(AudioListServices.quiz){
            navController.navigate(R.id.action_informationList_to_quiz);
        }else{
            navController.navigate(R.id.action_informationList_to_listItem);
        }

    }
}
