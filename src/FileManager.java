package main.analyzer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class FileManager {
    public static String readFile(Path path) throws IOException {
        return Files.readString(path);
    }

    public static void saveStatistics(String outputFileName, Map<String, Integer> statistics, int uniqueWords, int limit) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFileName))) {
            statistics.entrySet().stream()
                    .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // Sort (>1)
                    .limit(limit) // Top N
                    .forEach(entry -> writer.println(entry.getKey() + ": " + entry.getValue()));

            writer.println("Total unique words: " + uniqueWords);
            System.out.println("Statistics saved to " + outputFileName);
        }
    }

    public static String getOutputFileName(String bookTitle) {
        // Remove file extension, add "_statistic.txt"
        return bookTitle.replaceAll("\\.\\w+$", "") + "_statistic.txt";
    }
}