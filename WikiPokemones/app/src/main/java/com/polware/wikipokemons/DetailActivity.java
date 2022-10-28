package com.polware.wikipokemons;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.polware.wikipokemons.adapters.AbilitiesAdapter;
import com.polware.wikipokemons.databinding.ActivityDetailBinding;
import com.polware.wikipokemons.models.Abilities;
import com.polware.wikipokemons.models.DetailsPokemon;
import com.polware.wikipokemons.interfaces.PokemonService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding detailBinding;
    private PokemonService service;
    private Retrofit retrofit;
    private AbilitiesAdapter abilitiesAdapter;
    private DetailsPokemon detailsPokemon;
    private List<Abilities> abilitiesList;
    private Context context;
    private int id;
    private static final String TAG = "POKEMON_DETAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("Pokemon Details");
        detailBinding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(detailBinding.getRoot());
        id = getIdFromUrl(getIntent().getExtras().getString("url"));
        context = getApplicationContext();

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(PokemonService.class);
        abilitiesList = new ArrayList<>();
        Call<DetailsPokemon> detailsPokemonCall = service.getDetailPokemon(id);
        detailsPokemonCall.enqueue(new Callback<DetailsPokemon>() {
            @Override
            public void onResponse(Call<DetailsPokemon> call, Response<DetailsPokemon> response) {
                if(response.isSuccessful()){
                    Toast.makeText(DetailActivity.this, "Response satisfactorio", Toast.LENGTH_SHORT).show();
                    detailsPokemon = response.body();
                    abilitiesList = detailsPokemon.getAbilities();
                    abilitiesAdapter = new AbilitiesAdapter(abilitiesList, context);
                    detailBinding.recyclerViewAbilities.setAdapter(abilitiesAdapter);
                    detailBinding.recyclerViewAbilities.setLayoutManager(new LinearLayoutManager(context));
                    abilitiesAdapter.notifyDataSetChanged();

                    detailBinding.pokemonName.setText(detailsPokemon.getName());
                    detailBinding.pokemonAltura.setText(String.format("Height: %s", detailsPokemon.getHeight()));
                    detailBinding.pokemonPeso.setText(String.format("Weight: %s", detailsPokemon.getWeight()));
                    Glide.with(context)
                            .load(detailsPokemon.getSprites().getOther().getOfficial_artwork().getFront_default())
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(detailBinding.imagePokemon);
                }
                else {
                    Log.e(TAG, " onResponse Error: " +response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<DetailsPokemon> call, Throwable t) {
                Log.e(TAG, " onFailure: " +t.getMessage());
                Toast.makeText(DetailActivity.this, "Error al traer datos.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private int getIdFromUrl(String url) {
        String[] urlParts = url.split("/");
        return Integer.parseInt(urlParts[urlParts.length -1]);
    }

}