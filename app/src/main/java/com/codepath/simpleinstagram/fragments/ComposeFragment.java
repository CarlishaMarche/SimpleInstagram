package com.codepath.simpleinstagram.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codepath.simpleinstagram.R;

public class ComposeFragment extends Fragment {

    private final String TAG = "ComposeFragment";

    private RecyclerView timeline;
    private Button logOutBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        timeline = view.findViewById(R.id.timeline);
        logOutBtn = view.findViewById(R.id.logOutBtn);

        super.onViewCreated(view, savedInstanceState);
    }
}
