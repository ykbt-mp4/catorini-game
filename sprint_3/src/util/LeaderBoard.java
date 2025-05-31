package util;

import java.util.*;
import java.io.*;

public class LeaderBoard {
    private final Map<String, Integer> scores;
    private final int max_entries = 10;
    private final String filePath = "leaderboard.txt";

    public LeaderBoard() {
        scores = new HashMap<>();
        loadScores();
    }

    public void addScore(String playerName, int score) {
        if (playerName == null || playerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be null or empty");
        }

        String normalizedName = playerName.trim().toLowerCase();
        if (!scores.containsKey(normalizedName)) {
            scores.put(normalizedName, 0);
        }
        scores.put(normalizedName, scores.get(normalizedName) + score);
        saveScores();
    }

    public int topScore() {
        List<Integer> values = new ArrayList<>(scores.values());
        Collections.sort(values, Collections.reverseOrder());

        int sum = 0;
        int count = Math.min(max_entries, values.size());
        for (int i = 0; i < count; i++) {
            sum += values.get(i);
        }
        return sum;
    }

    public List<Map.Entry<String, Integer>> getTopPlayers() {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(scores.entrySet());
        entries.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        return entries.subList(0, Math.min(max_entries, entries.size()));
    }

    private void saveScores() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, Integer> entry : scores.entrySet()) {
                writer.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (IOException e) {
            System.err.println("Error saving leaderboard: " + e.getMessage());
        }
    }

    private void loadScores() {
        scores.clear(); // Clear existing scores before loading

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    try {
                        String playerName = parts[0];
                        int score = Integer.parseInt(parts[1]);
                        scores.put(playerName.toLowerCase(), score);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid data in leaderboard file: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading leaderboard: " + e.getMessage());
        }
    }

}