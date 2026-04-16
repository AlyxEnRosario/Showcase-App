package com.example.androidportfolio.ui.guess;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.example.androidportfolio.R;
import com.example.androidportfolio.data.LocalLeaderboardManager;
import com.example.androidportfolio.databinding.FragmentGuessBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GuessNumberFragment extends Fragment {

    private FragmentGuessBinding binding;
    private Random random = new Random();
    private int secretNumber;
    private int strikesLeft = 10;
    private int previousGuess = -1;
    private LocalLeaderboardManager leaderboardManager;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGuessBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        leaderboardManager = new LocalLeaderboardManager(sharedPreferences);

        initGame();

        binding.submitButton.setOnClickListener(v -> checkGuess());
        binding.newGameButton.setOnClickListener(v -> initGame());
        binding.viewLeaderboardButton.setOnClickListener(v -> showLeaderboard());

        return root;
    }

    private void initGame() {
        secretNumber = random.nextInt(100) + 1;
        strikesLeft = 10;
        previousGuess = -1;
        binding.guessInput.setText("");
        binding.feedback.setText("Guess a number between 1 and 100!");
        binding.strikeCounter.setText("Strikes left: " + strikesLeft);
        binding.newGameButton.setVisibility(View.GONE);
        binding.submitButton.setEnabled(true);
        binding.guessInput.setEnabled(true);
    }

    private void checkGuess() {
        String input = binding.guessInput.getText().toString().trim();

        if (input.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a number!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int guess = Integer.parseInt(input);

            if (guess < 1 || guess > 100) {
                binding.feedback.setText("Please guess between 1 and 100!");
                return;
            }

            if (guess == previousGuess) {
                binding.feedback.setText("You already guessed " + guess + "! Try a different number.");
                return;
            }

            previousGuess = guess;
            strikesLeft--;

            if (guess == secretNumber) {
                endGame(true);
            } else if (strikesLeft <= 0) {
                endGame(false);
            } else {
                String hint = guess < secretNumber ? "Too low! " : "Too high! ";
                binding.feedback.setText(hint + "Tries left: " + strikesLeft);
                binding.strikeCounter.setText("Strikes left: " + strikesLeft);
            }
        } catch (NumberFormatException e) {
            binding.feedback.setText("Please enter a valid number!");
        }
    }

    private void endGame(boolean won) {
        binding.submitButton.setEnabled(false);
        binding.guessInput.setEnabled(false);
        binding.newGameButton.setVisibility(View.VISIBLE);

        if (won) {
            binding.feedback.setText("🎉 You won! The number was " + secretNumber + "!");
        } else {
            binding.feedback.setText("💔 Game Over! The number was " + secretNumber);
        }
        
        promptForPlayerName(won);
    }

    private void promptForPlayerName(boolean won) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Save Your Score");
        builder.setMessage("Enter your name:");
        
        EditText input = new EditText(getContext());
        input.setHint("Player name");
        input.setText(sharedPreferences.getString("player_name", ""));
        builder.setView(input);
        
        builder.setPositiveButton("Save", (dialog, which) -> {
            String playerName = input.getText().toString().trim();
            if (playerName.isEmpty()) {
                playerName = "Anonymous";
            }
            saveScore(won, playerName);
        });
        
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void saveScore(boolean won, String playerName) {
        int score = won ? (11 - strikesLeft) * 10 : 0;

        // Save to phone memory
        sharedPreferences.edit().putString("player_name", playerName).apply();
        int highScore = sharedPreferences.getInt("high_score", 0);
        if (score > highScore) {
            sharedPreferences.edit().putInt("high_score", score).apply();
        }

        // Save to local leaderboard
        leaderboardManager.saveScore(playerName, score, won);
        Toast.makeText(getContext(), "Score saved!", Toast.LENGTH_SHORT).show();
    }

    private void showLeaderboard() {
        List<Map<String, Object>> topScores = leaderboardManager.getTopScores(10);
        
        StringBuilder leaderboard = new StringBuilder("Top 10 Scores:\n\n");
        if (topScores.isEmpty()) {
            leaderboard.append("No scores yet. Play a game!");
        } else {
            int rank = 1;
            for (Map<String, Object> scoreEntry : topScores) {
                String name = (String) scoreEntry.get("playerName");
                Number scoreNum = (Number) scoreEntry.get("score");
                long score = scoreNum.longValue();
                leaderboard.append(rank).append(". ").append(name).append(" - ").append(score).append("\n");
                rank++;
            }
        }
        binding.feedback.setText(leaderboard.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
