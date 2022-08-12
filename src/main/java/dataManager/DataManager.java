package dataManager;



import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class DataManager {


    public void writeToFile(String winnerScore, String winnerName) {
        try {
            FileWriter fileWriter = new FileWriter("stats.txt", true);
            fileWriter.append(winnerScore).append(",").append(winnerName).append("\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bestScoreRead() {
        List<String> lines;

        try {
            lines = Files.readAllLines(Paths.get("stats.txt"), StandardCharsets.UTF_8);
            bestScoreSort(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bestScoreSort(List<String> lines) {
        Map<Integer, String> scoreMap = new HashMap<>();
        System.out.println("TOP 10 SCORES: ");
        for (String line : lines) {
            String scoreLine = String.valueOf(line);
            String[] parts = String.valueOf(scoreLine).split(",");
            Integer score =Integer.valueOf(parts[0]);
            scoreMap.put(score, parts[1]);
        }
        Map<Integer, String> scoresMap = new TreeMap<>(scoreMap).descendingMap();
        printMap(scoresMap);
    }



    public static void printMap(Map<Integer,String> map) {
        Set<Map.Entry<Integer, String>> scoreSet= map.entrySet();
        int listPosition;
        int listLimit;
        listPosition = 0;
        listLimit = 10;
        System.out.println("========================");
        System.out.println("Place. Score => Player Name");
        System.out.println("========================");
        for (Map.Entry<Integer, String> entry: scoreSet ) {
             if( listPosition < listLimit ) {
                Integer key = entry.getKey();
                String value = entry.getValue();
                listPosition += 1;
                System.out.println(listPosition+". "+ key + " => " + value);
        }}
        System.out.println("========================");
    }

}
