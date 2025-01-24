import java.util.Map;

public class StatisticsPrinter {
    public static void printStatistics(Map<String, Integer> statistics, int uniqueWords, int limit) {
        System.out.println("\nTop " + limit + " words:");
        statistics.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(limit)
                .forEach(entry -> System.out.println(entry.getKey() + " -> " + entry.getValue()));

        System.out.println("\nTotal unique words: " + uniqueWords);
    }
}