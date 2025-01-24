import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

public class BookAnalyzer {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Enter the book title (or leave empty to exit): ");
            String bookTitle = scanner.nextLine();

            if (bookTitle.isEmpty()) {
                System.out.println("Exiting the program.");
                running = false;
                continue;
            }

            String booksDirectory = "src";
            Path bookPath = Paths.get(booksDirectory, bookTitle);
            try {
                String text = main.analyzer.FileManager.readFile(bookPath);
                int minWordLength = 2;
                Map<String, Integer> wordStatistics = TextProcessor.analyzeText(text, minWordLength);

                String outputFileName = main.analyzer.FileManager.getOutputFileName(bookTitle);
                int topWordsLimit = 10;
                main.analyzer.FileManager.saveStatistics(outputFileName, wordStatistics, wordStatistics.size(), topWordsLimit);

                StatisticsPrinter.printStatistics(wordStatistics, wordStatistics.size(), topWordsLimit);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}