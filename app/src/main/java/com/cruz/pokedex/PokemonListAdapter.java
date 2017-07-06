package com.cruz.pokedex;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Acer on 04/07/2017.
 */

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<PokemonModel> pokemons;
    private String nextUrl;
    private RequestQueue mRequestQueue;

    public PokemonListAdapter(Context context, ArrayList<PokemonModel> pokemons, String nextUrl){
        this.context = context;
        this.pokemons = pokemons;
        this.nextUrl = nextUrl;
    }

    @Override
    public PokemonListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if(viewType == R.layout.pokemon_card_list_layout){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_card_list_layout,parent,false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_button,parent,false);
        }

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PokemonListAdapter.ViewHolder holder, int position) {
        if(position == pokemons.size()) {
            holder.moreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "As you wish.", Toast.LENGTH_LONG).show();
                    requestPokemon(nextUrl);
                }
            });
        }
        else {
            Glide.with(context).load(pokemons.get(position).getPic()).into(holder.pokemonPic);
            String[] bits = pokemons.get(position).getUrl().split("/");
            String lastWord = bits[bits.length - 1];
            Log.d("charles", pokemons.get(position).getPic() + "");
            int num = Integer.parseInt(lastWord);
            DecimalFormat format = new DecimalFormat("#000");
            holder.pokemonNumber.setText(format.format(num));
            holder.pokemonName.setText(pokemons.get(position).getName().toUpperCase());
        }
    }

    @Override
    public int getItemCount() {
        return pokemons.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == pokemons.size()) ? R.layout.more_button : R.layout.pokemon_card_list_layout;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView pokemonNumber, pokemonName;
        ImageView pokemonPic;
        Button moreBtn;
        CardView cardy;

        public ViewHolder(View itemView) {
            super(itemView);
            pokemonNumber = itemView.findViewById(R.id.pokemonNumber);
            pokemonName = itemView.findViewById(R.id.pokemonName);
            pokemonPic = itemView.findViewById(R.id.pokemonPic);
            moreBtn = itemView.findViewById(R.id.moreBtn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context,PokemonDetailsActivity.class).putExtra("url",pokemons.get(getAdapterPosition()).getUrl()));
                }
            });
        }
    }

    public void requestPokemon(String string){

        // Instantiate the cache
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, string, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            nextUrl = response.getString("next");
                            JSONArray jsonArray = response.getJSONArray("results");

                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject result = jsonArray.getJSONObject(i);
                                final PokemonModel pokemonModel = new PokemonModel();
                                pokemonModel.setUrl(result.get("url").toString());
                                pokemonModel.setName(result.get("name").toString());

                                JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, result.get("url").toString(),
                                        null, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            JSONObject jsonObject2 = response.getJSONObject("sprites");
                                            String picUrl = jsonObject2.getString("front_default");
                                            pokemonModel.setPic(picUrl);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });

                                mRequestQueue.add(request2);

                                pokemons.add(pokemonModel);
                                notifyDataSetChanged();
                                Log.d("tyler","na abot siya dri");
//                                adapter = new PokemonListAdapter(context,pokemons,nextUrl);
//                                adapter.notifyDataSetChanged();
//                                recyclerView.setAdapter(adapter);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(jsonObjectRequest);
    }
}
