package com.example.androidportfolio.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.example.androidportfolio.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        
        // Get player name and update welcome
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String playerName = sharedPreferences.getString("player_name", "");
        
        if (!playerName.isEmpty()) {
            binding.titleWelcome.setText("Welcome to my portfolio, " + playerName + "!");
        }
        
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        // Update welcome text if they changed their name
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String playerName = sharedPreferences.getString("player_name", "");
        
        if (!playerName.isEmpty()) {
            binding.titleWelcome.setText("Welcome to my portfolio, " + playerName + "!");
        } else {
            binding.titleWelcome.setText(getString(com.example.androidportfolio.R.string.home_title));
        }
    }
}