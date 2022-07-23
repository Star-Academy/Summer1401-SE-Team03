import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    static ArrayList<String> allStopWords;
    static HashMap<String, ArrayList<Integer>> mapOfWords;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args){//*
        try {
            readAndSaveStopWords();
            readAllFilesAndSaveWords();
        } catch (IOException e) {
            e.printStackTrace();
        }
        handleInput();
    }

    private static void handleInput() {
        System.out.println("Please enter the number of exercise: 1 or 2?");
        String input = sc.nextLine();
        switch (input) {
            case "1" -> Practice1.starter();
            case "2" -> Practice2.starter();
            default -> System.out.println("wrong input!");
        }
    }

    private static void readAndSaveStopWords() throws IOException {
        allStopWords = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("src/stop_words_english.txt"));
        br.lines().forEach(stopWord -> allStopWords.add(stopWord.toUpperCase().replaceAll("\\p{Punct}", "")));
    }

    private static void readAllFilesAndSaveWords() throws FileNotFoundException {
        mapOfWords = new HashMap<>();
        String wordOfFile;
        Scanner sc;
        for (int i = 1; i <= 1000; i++) {
            sc = new Scanner(new FileReader("src/resources/file (" + i + ")"));
            while (sc.hasNext()) {
                wordOfFile = sc.next().toUpperCase().replaceAll("\\p{Punct}", "");
                addWordToMapOfWords(wordOfFile, i);//*
            }
        }
    }

    private static void addWordToMapOfWords(String wordOfFile, int fileNumber) {
        if (mapOfWords.containsKey(wordOfFile)) {//*
            if (!mapOfWords.get(wordOfFile).contains(fileNumber))
                mapOfWords.get(wordOfFile).add(fileNumber);
        } else if (!allStopWords.contains(wordOfFile)) {
            ArrayList<Integer> fileNumbersList = new ArrayList<>();
            fileNumbersList.add(fileNumber);
            mapOfWords.put(wordOfFile, fileNumbersList);
        }
    }
}