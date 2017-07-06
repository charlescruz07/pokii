package com.cruz.pokedex;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RequestQueue mRequestQueue;
    private String url = "http://pokeapi.co/api/v2/pokemon/";
    private ArrayList<PokemonModel> pokemons;
    private Context context;
    private ImageView leftBtn,rightBtn;
    private String prevUrl,nextUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.pokeball);

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

        pokemons = new ArrayList<>();
        context = this;
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new CustomLinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        requestPokemon(url);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    public void requestPokemon(String string){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, string, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            prevUrl = response.getString("previous");
                            nextUrl = response.getString("next");
                            Log.d("charles",prevUrl +"\n" + nextUrl);
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
                                adapter = new PokemonListAdapter(context,pokemons,nextUrl);
                                adapter.notifyDataSetChanged();
                                recyclerView.setAdapter(adapter);
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
