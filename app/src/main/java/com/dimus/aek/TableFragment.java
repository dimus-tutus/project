package com.dimus.aek;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TableFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TableAdapter mAdapter;
    private List<Table> mTable;

    public TableFragment(List<Table> table){
        mTable = table;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.table_recycle_view, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerViewTable);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new TableAdapter(getContext(), mTable);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}
