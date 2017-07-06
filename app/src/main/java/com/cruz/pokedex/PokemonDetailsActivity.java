package com.cruz.pokedex;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class PokemonDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView pokemonPic;
    private TextView pokemonNum,pokemonSpecie,pokemonHeight,pokemonWeight,pokemonDesc,statsBtn,movesBtn,locationBtn;
    private TextView topLabel,bottomLabel;
    private Context context;
    private Toolbar toolbar;
    private RequestQueue mRequestQueue;
    private String requestUrl;
    private PokemonDetailsModel pokemonDetailsModel;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_details);

        requestUrl = getIntent().getStringExtra("url");
        Log.d("aj",requestUrl);

        topLabel = (TextView) findViewById(R.id.topLabel);
        bottomLabel = (TextView) findViewById(R.id.labelz);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        context = this;
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        relativeLayout = (RelativeLayout) findViewById(R.id.act_details);
        pokemonPic = (ImageView) findViewById(R.id.pokemonPic);
        pokemonNum = (TextView) findViewById(R.id.pokemonNum);
        pokemonSpecie = (TextView) findViewById(R.id.pokemonSpecie);
        pokemonHeight = (TextView) findViewById(R.id.pokemonHeight);
        pokemonWeight = (TextView) findViewById(R.id.pokemonWeight);
        pokemonDesc = (TextView) findViewById(R.id.pokemonDesc);
        statsBtn = (TextView) findViewById(R.id.statsBtn);
        movesBtn = (TextView) findViewById(R.id.movesBtn);
        locationBtn = (TextView) findViewById(R.id.locationBtn);

        pokemonDetailsModel = new PokemonDetailsModel();

        requestPokemon(requestUrl);

    }

    @Override
    public void onClick(View view) {
        unSelect();
        switch (view.getId()){
            case R.id.statsBtn:
                select(statsBtn);
                bottomLabel.setText("STATS");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame,new StatsFragment().newInstance(pokemonDetailsModel.getStats()))
                        .commit();
                break;
            case R.id.movesBtn:
                bottomLabel.setText("MOVES");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame,new MovesFragment().newInstance(pokemonDetailsModel.getMoves()))
                        .commit();
                select(movesBtn);
                break;
            case R.id.locationBtn:
                bottomLabel.setText("VERSIONS");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame,new VersionFragment().newInstance(pokemonDetailsModel.getVersions()))
                        .commit();
                select(locationBtn);
                break;
        }
    }

    public void unSelect(){
        statsBtn.setBackground(ContextCompat.getDrawable(context,R.drawable.right_only_gray));
        statsBtn.setTextColor(Color.parseColor("#000000"));
        statsBtn.setTypeface(null,Typeface.NORMAL);
        movesBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
        movesBtn.setTextColor(Color.parseColor("#000000"));
        movesBtn.setTypeface(null,Typeface.NORMAL);
        locationBtn.setBackground(ContextCompat.getDrawable(context,R.drawable.left_only_gray));
        locationBtn.setTextColor(Color.parseColor("#000000"));
        locationBtn.setTypeface(null,Typeface.NORMAL);
    }

    public void select(View view){
        TextView textView = (TextView) view;
        textView.setBackgroundColor(Color.parseColor("#e74c3c"));
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextColor(Color.parseColor("#ffffff"));
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
                        try {
                            progressBar.setVisibility(View.GONE);
                            relativeLayout.setVisibility(View.VISIBLE);
                            Log.d("aj","ni sud sa response");
                            pokemonDetailsModel.setName(response.getString("name"));
                            Log.d("aj",pokemonDetailsModel.getName());
                            pokemonDetailsModel.setWeight(response.getString("weight"));
                            Log.d("aj",pokemonDetailsModel.getWeight());
                            pokemonDetailsModel.setHeight(response.getString("height"));
                            Log.d("aj",pokemonDetailsModel.getHeight());
                            String[] bits = requestUrl.split("/");
                            String lastWord = bits[bits.length - 1];
                            pokemonDetailsModel.setNumber(lastWord);
                            Log.d("aj",pokemonDetailsModel.getNumber());

                            JSONArray jsonArray = response.getJSONArray("stats");
                            ArrayList<String> stats = new ArrayList<>();
                            for(int i =0; i<jsonArray.length();i++){
                                stats.add(jsonArray.getJSONObject(i).getString("base_stat"));
                            }
                            pokemonDetailsModel.setStats(stats);
                            Log.d("aj",pokemonDetailsModel.getStats().get(1));

                            JSONObject jsonObject = response.getJSONObject("sprites");
                            pokemonDetailsModel.setPic(jsonObject.getString("front_default"));
                            Log.d("aj",pokemonDetailsModel.getPic());



                            JSONArray jsonArray1 = response.getJSONArray("moves");
                            ArrayList<String> moves = new ArrayList<>();
                            for(int i =0; i<jsonArray1.length(); i++){
                                JSONObject result = jsonArray1.getJSONObject(i);
                                Log.d("charles",result + "");
                                JSONObject result2 = result.getJSONObject("move");
                                moves.add(result2.get("name").toString());
                            }
                            pokemonDetailsModel.setMoves(moves);
                            Log.d("aj",pokemonDetailsModel.getMoves().get(1));

                            JSONArray jsonArray2 = response.getJSONArray("types");
                            for(int i =0; i<jsonArray2.length() ; i++){
                                JSONObject result = jsonArray2.getJSONObject(i);
                                JSONObject result2 = result.getJSONObject("type");
                                pokemonDetailsModel.setSpecie(result2.getString("name"));
                            }

                            ArrayList<String> versions = new ArrayList<>();
                            JSONArray jsonArray3 = response.getJSONArray("game_indices");
                            for (int i =0;i<jsonArray3.length();i++){
                                JSONObject result = jsonArray3.getJSONObject(i);
                                JSONObject result2 = result.getJSONObject("version");
                                versions.add(result2.getString("name"));
                            }
                            pokemonDetailsModel.setVersions(versions);

                            setItems();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(jsonObjectRequest);
    }

    public void setItems(){

        Glide.with(context).load(pokemonDetailsModel.getPic()).into(pokemonPic);
        int num = Integer.parseInt(pokemonDetailsModel.getNumber());
        DecimalFormat format = new DecimalFormat("#000");
        pokemonNum.setText(format.format(num));
        pokemonHeight.setText(pokemonDetailsModel.getHeight());
        pokemonWeight.setText(pokemonDetailsModel.getWeight());
        topLabel.setText(pokemonDetailsModel.getName().toUpperCase());
        pokemonSpecie.setText(pokemonDetailsModel.getSpecie().toUpperCase());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame,new StatsFragment().newInstance(pokemonDetailsModel.getStats()))
                .commit();
    }
}
