package dataManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * CLass responsible for handling file saving and reading
 */
public class DataManager {

    /**
     * Method used for writing winner score to the file
     *
     * @param winnerScore score of the winner
     */
    public static void writeToFile(String winnerScore) {
        try {
            FileWriter fileWriter = new FileWriter("stats.txt", true);
            fileWriter.append(winnerScore).append("\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    /**
     * Method responsible for reading best scores from the file
     */
    public static void bestScoreRead() {
        List<String> lines = Collections.emptyList();
        List<Integer> scores = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get("stats.txt"), StandardCharsets.UTF_8);

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Best scores: ");
        for (String line : lines) {
            int score =Integer.parseInt(line);
            scores.add(score);
        }
        scores.sort(Collections.reverseOrder());
        List<Integer> bestScores = scores.subList(0, 10);
        for (Integer score : bestScores) {
            System.out.println(score);
        }



    };};
