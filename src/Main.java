import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    static ArrayList<String> allStopWords;
    static HashMap<String, ArrayList<Integer>> mapOfWords;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        readAndSaveStopWords();
        readAllFilesAndSaveWords();
        handleInput();
    }

    private static void handleInput() {
        System.out.println("Please enter the number of exercise: 1 or 2?");
        String input = sc.nextLine();
        if (input.equals("1"))
            Practice1.starter();
        else if (input.equals("2"))
            Practice2.starter();
        else
            System.out.println("wrong input!");
    }

    private static void readAllFilesAndSaveWords() {
        mapOfWords = new HashMap<>();
        for (int i = 1; i <= 1000; i++) {
            try {
                Scanner sc = new Scanner(new FileReader("src/resources/file (" + i + ")"));
                while (sc.hasNext()) {
                    String word = sc.next().toUpperCase().replaceAll("\\p{Punct}", "");
                    addWordToMapOfWords(word, i);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void addWordToMapOfWords(String word, int fileNumber) {
        if (mapOfWords.get(word) != null) {
            if (!mapOfWords.get(word).contains(fileNumber))
                mapOfWords.get(word).add(fileNumber);
        } else {
            if (!allStopWords.contains(word)) {
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(fileNumber);
                mapOfWords.put(word, temp);
            }
        }
    }

    private static void readAndSaveStopWords() {
        allStopWords = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/stop_words_english.txt"));
            String word;
            while ((word = br.readLine()) != null) {
                allStopWords.add(word.toUpperCase().replaceAll("\\p{Punct}", ""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}