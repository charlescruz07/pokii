package com.cruz.pokedex;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import junit.runner.Version;

import java.util.ArrayList;

/**
 * Created by Acer on 06/07/2017.
 */

public class VersionFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> versions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.moves_fragment_layout,container,false);

        this.versions = getArguments().getStringArrayList("versions");
        recyclerView = rootView.findViewById(R.id.recyclerViewz);
        layoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MovesAdapter(rootView.getContext(),versions);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public static VersionFragment newInstance(ArrayList<String> versions) {

        Bundle args = new Bundle();
        args.putStringArrayList("versions",versions);
        VersionFragment fragment = new VersionFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
