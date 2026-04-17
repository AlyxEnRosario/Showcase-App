package com.example.androidportfolio.data;

import androidx.annotation.NonNull;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Firebase-based leaderboard manager using Firestore.
 * Saves and retrieves scores from the cloud.
 */
public class FirebaseLeaderboardManager {
    
    private static final String COLLECTION_NAME = "leaderboard";
    private final FirebaseFirestore db;
    
    public interface LeaderboardCallback {
        void onSuccess(List<Map<String, Object>> scores);
        void onFailure(String error);
    }
    
    public interface SaveCallback {
        void onSuccess();
        void onFailure(String error);
    }
    
    public FirebaseLeaderboardManager() {
        this.db = FirebaseFirestore.getInstance();
    }
    
    /**
     * Save a score to Firebase Firestore
     */
    public void saveScore(String playerName, int score, boolean won, @NonNull SaveCallback callback) {
        ScoreEntry entry = new ScoreEntry(playerName, score, won);
        
        db.collection(COLLECTION_NAME)
            .add(entry)
            .addOnSuccessListener(documentReference -> {
                callback.onSuccess();
            })
            .addOnFailureListener(e -> {
                callback.onFailure(e.getMessage());
            });
    }
    
    /**
     * Get top N scores sorted by score descending, then by timestamp
     */
    public void getTopScores(int limit, @NonNull LeaderboardCallback callback) {
        db.collection(COLLECTION_NAME)
            .orderBy("score", Query.Direction.DESCENDING)
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .limit(limit)
            .get()
            .addOnSuccessListener((QuerySnapshot task) -> {
                List<Map<String, Object>> scores = new ArrayList<>();
                for (int i = 0; i < task.getDocuments().size(); i++) {
                    scores.add(task.getDocuments().get(i).getData());
                }
                callback.onSuccess(scores);
            })
            .addOnFailureListener(e -> {
                callback.onFailure(e.getMessage());
            });
    }
    
    /**
     * Get scores for a specific player
     */
    public void getPlayerScores(String playerName, @NonNull LeaderboardCallback callback) {
        db.collection(COLLECTION_NAME)
            .whereEqualTo("playerName", playerName)
            .orderBy("score", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener((QuerySnapshot task) -> {
                List<Map<String, Object>> scores = new ArrayList<>();
                for (int i = 0; i < task.getDocuments().size(); i++) {
                    scores.add(task.getDocuments().get(i).getData());
                }
                callback.onSuccess(scores);
            })
            .addOnFailureListener(e -> {
                callback.onFailure(e.getMessage());
            });
    }
    
    /**
     * Delete all scores (admin only - use with caution)
     */
    public void clearLeaderboard(@NonNull SaveCallback callback) {
        db.collection(COLLECTION_NAME)
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                for (int i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                    queryDocumentSnapshots.getDocuments().get(i).getReference().delete();
                }
                callback.onSuccess();
            })
            .addOnFailureListener(e -> {
                callback.onFailure(e.getMessage());
            });
    }
}
