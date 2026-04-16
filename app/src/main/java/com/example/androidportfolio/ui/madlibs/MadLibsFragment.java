package com.example.androidportfolio.ui.madlibs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.androidportfolio.R;
import com.example.androidportfolio.databinding.FragmentMadlibsBinding;

public class MadLibsFragment extends Fragment {

    private FragmentMadlibsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMadlibsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.generateButton.setOnClickListener(v -> generateStory());

        return root;
    }

    private void generateStory() {
        // Get inputs
        String adjective1 = binding.adjective1.getText().toString().trim();
        String noun1 = binding.noun1.getText().toString().trim();
        String adjective2 = binding.adjective2.getText().toString().trim();
        String noun2 = binding.noun2.getText().toString().trim();
        String verb = binding.verb.getText().toString().trim();
        String adjective3 = binding.adjective3.getText().toString().trim();

        // Check inputs
        if (adjective1.isEmpty() || noun1.isEmpty() || adjective2.isEmpty() || 
            noun2.isEmpty() || verb.isEmpty() || adjective3.isEmpty()) {
            binding.storyDisplay.setText("Please fill in all fields!");
            return;
        }

        // Make the story
        String story = "There once was a " + adjective1 + " " + noun1 + " who loved to " + verb + " " +
                "with a " + adjective2 + " " + noun2 + ". They were very " + adjective3 + " and " +
                "had many adventures in the forest. The end!";

        binding.storyDisplay.setText(story);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
