package dataManager;
import utilities.Display;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * CLass responsible for handling file saving and reading
 */
public class DataManager {
    /**
     * Instance of Display class for displaying info to the user
     *
     * @see Display
     */
    static Display printer = new Display();
    /**
     * Method used for writing winner score to the file
     *
     * @param winnerScore score of the winner
     * @param winnerName name of the winner
     */
    public void writeToFile(String winnerScore, String winnerName) {
        try {
            FileWriter fileWriter = new FileWriter("stats.txt", true);
            fileWriter.append(winnerScore).append(",").append(winnerName).append("\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 

    /**
     * Method responsible for reading best scores from the file
     */
    public void bestScoreRead() {
        List<String> lines;

        try {
            lines = Files.readAllLines(Paths.get("stats.txt"), StandardCharsets.UTF_8);
            bestScoreSort(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method responsible for sorting scores from the file
     * @param lines lines
     */
    public void bestScoreSort(List<String> lines) {
        Map<Integer, String> scoreMap = new HashMap<>();
        printer.consolePrint("TOP 10 SCORES: ");
        for (String line : lines) {
            String scoreLine = String.valueOf(line);
            String[] parts = String.valueOf(scoreLine).split(",");
            Integer score =Integer.valueOf(parts[0]);
            scoreMap.put(score, parts[1]);
        }
        Map<Integer, String> scoresMap = new TreeMap<>(scoreMap).descendingMap();
        printMap(scoresMap);
    }


    /**
     * Method printing the map
     * @param map map
     */
    public static void printMap(Map<Integer,String> map) {
        Set<Map.Entry<Integer, String>> scoreSet= map.entrySet();
        int listPosition;
        int listLimit;
        listPosition = 0;
        listLimit = 10;
        printer.consolePrint("========================");
        printer.consolePrint("Place. Score => Player Name");
        printer.consolePrint("========================");
        for (Map.Entry<Integer, String> entry: scoreSet ) {
             if( listPosition < listLimit ) {
                Integer key = entry.getKey();
                String value = entry.getValue();
                listPosition += 1;
                 printer.consolePrint(listPosition+". "+ key + " => " + value);
        }}
        printer.consolePrint("========================");

    }

}

