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


    private void saveScores() {
            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
                scores.entrySet().stream()
                        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()) // sort high to low
                        .limit(max_entries) // top 10
                        .forEach(entry -> writer.println(entry.getKey() + ": " + entry.getValue()));
            } catch (IOException e) {
                System.err.println("Error saving leaderboard: " + e.getMessage());
            }
        }


    private void loadScores() {
        scores.clear(); // Clear existing scores before loading

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
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

    public String readLeaderboardFile() {
        StringBuilder content = new StringBuilder();
        File file = new File(filePath);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                int index = 1;
                while ((line = reader.readLine()) != null) {
                    content.append(index++).append(". ").append(line).append("\n");
                }
            } catch (IOException e) {
                content.append("Error reading leaderboard file.");
            }
        } else {
            content.append("No leaderboard data available.");
        }

        return content.toString();
    }


}