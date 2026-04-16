package com.example.androidportfolio.ui.pokemon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.androidportfolio.databinding.FragmentPokemonBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonFragment extends Fragment {

    private FragmentPokemonBinding binding;
    private List<PokemonResponse> pokemonList = new ArrayList<>();
    private PokemonCardAdapter adapter;
    private PokeApiService apiService;

    // Favorite pokemon to show
    private final int[] favoriteIds = {1, 4, 7, 25, 39, 54, 58};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPokemonBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupRetrofit();
        adapter = new PokemonCardAdapter(pokemonList);
        binding.pokemonViewPager.setAdapter(adapter);

        fetchPokemonData();

        return root;
    }

    private void setupRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(PokeApiService.class);
    }

    private void fetchPokemonData() {
        for (int id : favoriteIds) {
            apiService.getPokemon(String.valueOf(id)).enqueue(new Callback<PokemonResponse>() {
                @Override
                public void onResponse(@NonNull Call<PokemonResponse> call, @NonNull Response<PokemonResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        pokemonList.add(response.body());
                        adapter.notifyItemInserted(pokemonList.size() - 1);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PokemonResponse> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Error fetching Pokemon: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
