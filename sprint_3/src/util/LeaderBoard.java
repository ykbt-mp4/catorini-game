package util;

import java.util.*;
import java.io.*;

/**
 * The LeaderBoard class manages player scores and saves
 * them in a local file called "leaderboard.txt". It allows adding scores,
 * retrieving the leaderboard, and maintains the top 10 players.
 */
public class LeaderBoard {

    //A map storing player names (lowercased) and their corresponding scores.
    private final Map<String, Integer> scores;

    private final int max_entries = 10;
    private final String filePath = "leaderboard.txt";

    /**
     * Constructs a new Leaderboard instance.
     * Loads existing scores from the file, if available.
     */
    public LeaderBoard() {
        scores = new HashMap<>();
        loadScores();
    }

    /**
     * Adds a score for the specified player. If the player does not exist,
     * they are added. Existing players have their scores incremented each time they win.
     * @param playerName the name of the player
     * @param score the score to add
     * @throws IllegalArgumentException if the player name is null or empty
     */
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

    /**
     * Saves the current scores to the leaderboard file, sorted from highest to lowest.
     * Only the top max_entries players are saved.
     */
    private void saveScores() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            scores.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(max_entries)
                    .forEach(entry -> writer.println(entry.getKey() + ": " + entry.getValue()));
        } catch (IOException e) {
            System.err.println("Error saving leaderboard: " + e.getMessage());
        }
    }

    /**
     * Loads scores from the leaderboard file into memory.
     * Invalid lines are ignored with an error message.
     */
    private void loadScores() {
        scores.clear();

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

    /**
     * Reads the leaderboard file and returns its contents in a formatted string.
     * Each entry is numbered. If the file does not exist, a message is returned.
     * @return the formatted leaderboard content
     */
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