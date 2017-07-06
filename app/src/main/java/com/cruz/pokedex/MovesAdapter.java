package com.cruz.pokedex;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Acer on 05/07/2017.
 */

public class MovesAdapter extends RecyclerView.Adapter<MovesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> moves;

    public MovesAdapter(Context context, ArrayList<String> moves){
        this.context = context;
        this.moves = moves;
        Log.d("allyn",moves.size() + "");
    }

    @Override
    public MovesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_card_list_layout,parent,false);
        ViewHolder v = new ViewHolder(rootView);
        return v;
    }

    @Override
    public void onBindViewHolder(MovesAdapter.ViewHolder holder, int position) {
        DecimalFormat format = new DecimalFormat("#000");
        holder.pokemonNumber.setText(format.format(position));
        holder.pokemonName.setText(moves.get(position).toUpperCase());
        Glide.with(context).load(R.drawable.ic_pokeball).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return moves.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView pokemonNumber, pokemonName;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            pokemonNumber = itemView.findViewById(R.id.pokemonNumber);
            pokemonName = itemView.findViewById(R.id.pokemonName);
            imageView = itemView.findViewById(R.id.pokemonPic);
        }
    }
}
