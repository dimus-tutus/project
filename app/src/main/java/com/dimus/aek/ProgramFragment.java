package com.dimus.aek;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProgramFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ProgramAdapter mAdapter;
    private List<Match> mMatches;

    public ProgramFragment(List<Match> matches){
        mMatches = matches;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.matches_recycle_view, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerViewMatches);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new ProgramAdapter(getContext(), mMatches, 1);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}
