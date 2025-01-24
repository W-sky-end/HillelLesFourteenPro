import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TextProcessor {
    public static Map<String, Integer> analyzeText(String text, int minWordLength) {
        String[] words = text.toLowerCase()
                .replaceAll("[^\\p{L}\\p{Nd}\\s]", "")
                .split("\\s+");

        Map<String, Integer> wordCount = new HashMap<>();
        for (String word : words) {
            if (word.length() >= minWordLength) {
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }
        return wordCount;
    }
}