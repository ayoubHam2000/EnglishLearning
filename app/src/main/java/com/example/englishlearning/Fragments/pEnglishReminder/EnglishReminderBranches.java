package com.example.englishlearning.Fragments.pEnglishReminder;


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

import com.example.englishlearning.Adapters.BranchesAdapter;
import com.example.englishlearning.Modles.EnglishReminderStruct;
import com.example.englishlearning.R;
import com.example.englishlearning.Services.EnglishReminderServices;
import com.example.englishlearning.Utilities.Const;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnglishReminderBranches extends Fragment implements BranchesAdapter.onItemClick{


    //view
    private RecyclerView branchReminderRecyclerView;
    private NavController navController;


    //var
    private BranchesAdapter branchesAdapter;

    public EnglishReminderBranches() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.english_reminder_branch_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initVar();

    }

    private void initView(View view){
        branchReminderRecyclerView = view.findViewById(R.id.branchReminderRecyclerView);
        navController = Navigation.findNavController(view);

    }

    private void initVar(){
        initBranchesRecyclerView();
    }

    //-----------------------------
    //view

    private void initBranchesRecyclerView(){
        branchesAdapter = new BranchesAdapter(EnglishReminderServices.list, this);

        branchReminderRecyclerView.setAdapter(branchesAdapter);
        branchReminderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onClickListener() {
        navController.navigate(R.id.action_englishReminderBranches_to_englishReminderListDisplay);
    }
}
