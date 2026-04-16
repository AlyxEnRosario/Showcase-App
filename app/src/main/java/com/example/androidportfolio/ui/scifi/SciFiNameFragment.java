package com.example.androidportfolio.ui.scifi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.androidportfolio.R;
import com.example.androidportfolio.databinding.FragmentScifiBinding;

import java.util.Random;

public class SciFiNameFragment extends Fragment {

    private FragmentScifiBinding binding;
    private Random random = new Random();

    // Sci-fi name parts
    private final String[] prefixes = {"Zar", "Xal", "Zyx", "Kry", "Nyx", "Vor", "Thar", "Krax"};
    private final String[] middles = {"thor", "lon", "vex", "sin", "tar", "kor", "nyx", "mul"};
    private final String[] suffixes = {"ian", "an", "or", "ix", "us", "ex", "on", "ax"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentScifiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.generateButton.setOnClickListener(v -> generateNewName());

        return root;
    }

    private void generateNewName() {
        String playerInput = binding.prefixInput.getText().toString().trim();

        if (playerInput.isEmpty()) {
            binding.generatedName.setText("Please enter a word!");
            binding.generatedName.setVisibility(View.VISIBLE);
            return;
        }

        // Mix player input with random sci-fi parts
        String randomPrefix = prefixes[random.nextInt(prefixes.length)];
        String randomSuffix = suffixes[random.nextInt(suffixes.length)];
        
        // Randomly decide how to mix them
        int mixType = random.nextInt(3);
        String scifiName;
        
        if (mixType == 0) {
            scifiName = randomPrefix + playerInput + randomSuffix;
        } else if (mixType == 1) {
            scifiName = playerInput + randomSuffix;
        } else {
            scifiName = randomPrefix + playerInput;
        }
        
        binding.generatedName.setText(scifiName);
        binding.generatedName.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
