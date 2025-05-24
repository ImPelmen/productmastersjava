package six.medium;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
        Set<String> uniqueWords = new HashSet<>();
        Map<String, Integer> wordCounter = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("F:\\java\\learning\\ProductMasters\\git\\Module 6\\Homework\\src\\six\\medium\\words.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    word = word.replaceAll("[.,?!]", "").toLowerCase();
                    if (!uniqueWords.add(word)) {
                        wordCounter.put(word, wordCounter.get(word) + 1);
                    } else {
                        wordCounter.put(word, 1);
                    }
                }
            }
            System.out.println(wordCounter);
            System.out.println(uniqueWords);
        } catch (Exception e) {
            throw e;
        }
    }
}
