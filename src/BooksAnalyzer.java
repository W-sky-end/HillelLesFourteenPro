import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.awt.SystemColor.text;

public class BooksAnalyzer {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // ввод данных мною
         boolean running = true;
         while (running) {   // без true , как было сказано в прошлых уроках
             System.out.println("Enter the book title: ");
             String bookTitle = sc.nextLine();

             if (bookTitle.isEmpty()) {
                 System.out.println("The book title is empty."); // ниесли  ничегг не ввели
                 running = false;
                 continue;
             }
             Path bookPath = Paths.get("src",bookTitle); //Интерфейс который предоставляет собой путь к файлу
                                                              // Paths.get - формиравоние пути

             try {
                 String text = Files.readString(bookPath); // читает указанный файл
                 System.out.println("Read text: " + text);

                 Map<String, Integer> wordStatistic = analyzeText(text);// работа с текстом разделение слов , уберает лишние символы
                 int uniqueWordCount = wordStatistic.size();

                 saveStatistic(bookTitle, wordStatistic, uniqueWordCount); // сохранение статистики

                 printStatistic(wordStatistic, uniqueWordCount); // вывод статистики

             }catch (IOException e) {
                 System.out.println("The book path does not exist.  " + e.getMessage());
             }
         }
    }

    private static Map<String, Integer> analyzeText(String text) {
        String[] words = text.toLowerCase().replaceAll("[^\\p{L}\\p{Nd}\\s]", "").split("\\s+"); //удаляем символы / текст на слова

        return Arrays.stream(words)                            // поток Stream из массива строк
                .filter(word -> word.length() > 2)     // отрезаем что меньше 2 букв
                .collect(Collectors.toMap(                    // сохраняем
                        word -> word,
                        word -> 1,
                        Integer::sum                          //складываем  слова
                ));
    }


    private static void saveStatistic(String bookName, Map<String, Integer> wordStatistic, int uniqueWordCount) {
        String outputFileName = bookName.replaceAll("\\.\\w+$", "") + "_statistic.txt"; // убрали txt ставим _statistic.txt
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFileName))) { //FileWr - создаем файл для записи
            wordStatistic.entrySet()             //поток
                    .stream()
                    .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))  // сортируем (>1)
                    .limit(10)    // max
                    .forEach(entry -> writer.println(entry.getKey() + ": " + entry.getValue()));

            writer.println("All unique words: " + uniqueWordCount);     // all uniq
            System.out.println("Saved statistics to " + outputFileName);  // куда сохранено
        }catch (IOException e) {
            System.out.println("Error while saving statistics." + e.getMessage());   // обработка ошибки при сейве
        }
        }


private static void printStatistic(Map<String, Integer> wordStatistic, int uniqueWordCount) {
    System.out.println("\nTop 10 words:");   // наш топ 10
    wordStatistic.entrySet()             // пеобразуем в Map(Str,Int) слово , кол-вл раз
            .stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // сорт по кол-ву раз
            .limit(10)
            .forEach(entry -> System.out.println(entry.getKey() + " -> " + entry.getValue()));  // вывод слова , кол-во

    System.out.println("\nUnique words: " + uniqueWordCount); // вывод
}
}