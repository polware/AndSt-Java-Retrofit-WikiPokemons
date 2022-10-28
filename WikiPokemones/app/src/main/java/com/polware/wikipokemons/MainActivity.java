package com.polware.wikipokemons;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.polware.wikipokemons.adapters.PokemonAdapter;
import com.polware.wikipokemons.databinding.ActivityMainBinding;
import com.polware.wikipokemons.models.Pokemon;
import com.polware.wikipokemons.models.PokemonResponse;
import com.polware.wikipokemons.interfaces.PokemonService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Retrofit retrofit;
    private static final String TAG = "POKEMON_WIKI";
    private PokemonAdapter pokemonAdapter;
    private ArrayList<Pokemon> pokemonList;
    //Para cargar pokemones por lotes
    private int offset;
    private boolean validLoadPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pokemonAdapter = new PokemonAdapter(this);
        binding.recyclerViewPokemones.setAdapter(pokemonAdapter);
        events();
        binding.recyclerViewPokemones.setHasFixedSize(true);
        //Definimos el Grid
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        binding.recyclerViewPokemones.setLayoutManager(gridLayoutManager);

        binding.recyclerViewPokemones.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int visibleItemCount = gridLayoutManager.getItemCount();
                    int totalItemCount = gridLayoutManager.getItemCount();
                    int pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();
                    if (validLoadPokemon) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, "Llegamos al final");
                            validLoadPokemon = false;
                            offset += 20;
                            getDataList(offset);
                        }
                    }
                }
            }
        });

        //Conectamos con la API
        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        validLoadPokemon = true;
        offset = 0;
        getDataList(offset);
    }

    //Nos conectamos a la interfaz PokemonService
    private void getDataList(int offset){
        PokemonService service = retrofit.create(PokemonService.class);
        Call<PokemonResponse> pokemonResponseCall = service.getListPokemon(offset, 20);

        pokemonResponseCall.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                validLoadPokemon = true;
                if(response.isSuccessful()){
                    PokemonResponse pokemonResponse = response.body();
                    pokemonList = pokemonResponse.getResults();
                    pokemonAdapter.addListPokemon(pokemonList);
                }
                else {
                    Log.e(TAG, " onResponse: " +response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                validLoadPokemon = true;
                Log.e(TAG, " onFailure: " +t.getMessage());
            }
        });
    }

    private void events() {
        pokemonAdapter.setOnPokemonSelected((position, pokemon) -> {
            //Muestra la info del pokemon seleccionado
            Intent detalle = new Intent(this, DetailActivity.class);
            detalle.putExtra("url", pokemon.getUrl());
            startActivity(detalle);
        });
    }

}