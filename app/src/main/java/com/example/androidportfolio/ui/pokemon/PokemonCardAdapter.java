package com.example.androidportfolio.ui.pokemon;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.androidportfolio.databinding.ItemPokemonCardBinding;
import com.bumptech.glide.Glide;

import java.util.List;

public class PokemonCardAdapter extends RecyclerView.Adapter<PokemonCardAdapter.PokemonViewHolder> {

    private List<PokemonResponse> pokemonList;

    public PokemonCardAdapter(List<PokemonResponse> pokemonList) {
        this.pokemonList = pokemonList;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPokemonCardBinding binding = ItemPokemonCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PokemonViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        holder.bind(pokemonList.get(position));
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public static class PokemonViewHolder extends RecyclerView.ViewHolder {
        private ItemPokemonCardBinding binding;

        public PokemonViewHolder(ItemPokemonCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(PokemonResponse pokemon) {
            binding.pokemonName.setText(pokemon.getName().toUpperCase());
            binding.pokemonId.setText("ID: " + pokemon.getId());

            // Load pokemon image
            if (pokemon.getSprites() != null && pokemon.getSprites().front_default != null) {
                Glide.with(binding.getRoot().getContext())
                        .load(pokemon.getSprites().front_default)
                        .into(binding.pokemonImage);
            }

            // Show types
            StringBuilder types = new StringBuilder("Types: ");
            if (pokemon.getTypes() != null) {
                for (int i = 0; i < pokemon.getTypes().size(); i++) {
                    if (i > 0) types.append(", ");
                    types.append(pokemon.getTypes().get(i).type.name);
                }
            }
            binding.pokemonTypes.setText(types.toString());

            // Show stats
            StringBuilder stats = new StringBuilder("Stats:\n");
            if (pokemon.getStats() != null) {
                for (PokemonResponse.Stat stat : pokemon.getStats()) {
                    stats.append(stat.stat.name).append(": ").append(stat.base_stat).append("\n");
                }
            }
            binding.pokemonStats.setText(stats.toString());
        }
    }
}
