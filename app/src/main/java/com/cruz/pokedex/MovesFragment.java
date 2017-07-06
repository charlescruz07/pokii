package com.cruz.pokedex;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Acer on 05/07/2017.
 */

public class MovesFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> moves;

    public static MovesFragment newInstance(ArrayList<String> moves) {
        
        Bundle args = new Bundle();
        MovesFragment fragment = new MovesFragment();
        args.putStringArrayList("moves",moves);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.moves_fragment_layout,container,false);

        this.moves = getArguments().getStringArrayList("moves");
        Log.d("allyn",moves.size() + "");
        Log.d("allyn",moves.get(1));
        recyclerView = rootView.findViewById(R.id.recyclerViewz);
        layoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MovesAdapter(rootView.getContext(),moves);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
