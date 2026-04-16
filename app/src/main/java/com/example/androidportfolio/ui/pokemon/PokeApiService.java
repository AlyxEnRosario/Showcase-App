package com.example.androidportfolio.ui.pokemon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokeApiService {
    @GET("pokemon/{id}")
    Call<PokemonResponse> getPokemon(@Path("id") String id);
}
