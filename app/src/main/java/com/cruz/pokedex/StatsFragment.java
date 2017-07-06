package com.cruz.pokedex;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Acer on 05/07/2017.
 */

public class StatsFragment extends Fragment {

    TextView speedLbl,specialDefenseLbl,specialAttackLbl,defenseLbl,attackLbl,hpLbl;
    ArrayList<String> stats;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.stats_fragment_layout,container,false);

        this.stats = getArguments().getStringArrayList("stats");

        speedLbl = rootView.findViewById(R.id.speedLbl);
        specialDefenseLbl = rootView.findViewById(R.id.specialDefenseLbl);
        specialAttackLbl = rootView.findViewById(R.id.specialAttackLbl);
        defenseLbl = rootView.findViewById(R.id.defenseLbl);
        attackLbl = rootView.findViewById(R.id.attackLbl);
        hpLbl = rootView.findViewById(R.id.hpLbl);

        speedLbl.setText(stats.get(0));
        specialDefenseLbl.setText(stats.get(1));
        specialAttackLbl.setText(stats.get(2));
        defenseLbl.setText(stats.get(3));
        attackLbl.setText(stats.get(4));
        hpLbl.setText(stats.get(5));

        return rootView;
    }

    public static StatsFragment newInstance(ArrayList<String> stats) {

        Bundle args = new Bundle();
        args.putStringArrayList("stats",stats);
        StatsFragment fragment = new StatsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
