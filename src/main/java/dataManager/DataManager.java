package dataManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;


public class DataManager {


    public static void writeToFile(String winnerScore) {
        try {
            FileWriter fileWriter = new FileWriter("stats.txt", true);
            fileWriter.append(winnerScore).append("\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

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
