package com.polware.wikipokemons.interfaces;

import com.polware.wikipokemons.models.DetailsPokemon;
import com.polware.wikipokemons.models.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokemonService {
    @GET("pokemon")
    Call<PokemonResponse> getListPokemon(@Query("offset") int offset, @Query("limit") int limit);

    @GET("pokemon/{id}/")
    Call<DetailsPokemon> getDetailPokemon(@Path("id") int id);
}
