package com.example.androidportfolio.ui.scifi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.androidportfolio.R;
import com.example.androidportfolio.databinding.FragmentScifiBinding;

public class SciFiNameFragment extends Fragment {

    private FragmentScifiBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentScifiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.generateButton.setOnClickListener(v -> generateSciFiName());

        return root;
    }

    private void generateSciFiName() {
        // Capture all input from EditText fields
        String firstName = binding.firstNameInput.getText().toString().trim();
        String lastName = binding.lastNameInput.getText().toString().trim();
        String city = binding.cityInput.getText().toString().trim();
        String school = binding.schoolInput.getText().toString().trim();
        String pet = binding.petInput.getText().toString().trim();
        String character = binding.characterInput.getText().toString().trim();

        // Validate all fields are filled
        if (firstName.isEmpty() || lastName.isEmpty() || city.isEmpty() || 
            school.isEmpty() || pet.isEmpty() || character.isEmpty()) {
            // Show error - all fields required
            binding.resultsContainer.setVisibility(View.GONE);
            return;
        }

        // Generate SciFi First Name: first 2 chars of first name + first 3 chars of last name
        String scifiFirstName = generateFirstName(firstName, lastName);

        // Generate SciFi Last Name: first 2 chars of city + first 3 chars of school
        String scifiLastName = generateLastName(city, school);

        // Generate SciFi Origin: mix pet and character together
        String scifiOrigin = generateOrigin(pet, character);

        // Display results
        binding.generatedScifiFirst.setText(scifiFirstName);
        binding.generatedScifiLast.setText(scifiLastName);
        binding.generatedScifiOrigin.setText(scifiOrigin);
        binding.resultsContainer.setVisibility(View.VISIBLE);
    }

    /**
     * Generate SciFi first name by taking:
     * - First 2 characters of first name
     * - First 3 characters of last name
     */
    private String generateFirstName(String firstName, String lastName) {
        String firstNamePart = firstName.length() >= 2 ? firstName.substring(0, 2) : firstName;
        String lastNamePart = lastName.length() >= 3 ? lastName.substring(0, 3) : lastName;
        return firstNamePart + lastNamePart;
    }

    /**
     * Generate SciFi last name by taking:
     * - First 2 characters of city
     * - First 3 characters of school
     */
    private String generateLastName(String city, String school) {
        String cityPart = city.length() >= 2 ? city.substring(0, 2) : city;
        String schoolPart = school.length() >= 3 ? school.substring(0, 3) : school;
        return cityPart + schoolPart;
    }

    /**
     * Generate SciFi origin by mixing pet and character together
     * Interleaves characters from both to create a sci-fi portmanteau
     */
    private String generateOrigin(String pet, String character) {
        StringBuilder result = new StringBuilder();
        int maxLength = Math.max(pet.length(), character.length());
        
        // Interleave characters from both pet and character
        for (int i = 0; i < maxLength; i++) {
            if (i < pet.length()) {
                result.append(pet.charAt(i));
            }
            if (i < character.length()) {
                result.append(character.charAt(i));
            }
        }
        
        return result.toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
