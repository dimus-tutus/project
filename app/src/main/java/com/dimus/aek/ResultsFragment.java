package com.dimus.aek;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResultsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ResultsAdapter mAdapter;
    private List<ResultM> mResults;

    public ResultsFragment(List<ResultM> results){
        mResults = results;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.results_recycle_view, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerViewResults);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new ResultsAdapter(getContext(),mResults);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}
