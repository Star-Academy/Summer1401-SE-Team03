import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    static ArrayList<String> allStopWords;
    static HashMap<String, ArrayList<Integer>> mapOfWords;

    public static void main(String[] args) {
        readStopWords();
        readAllFilesAndSaveWords();
        handleInput();
    }

    private static void handleInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the number of exercise: 1 or 2?");
        int input = sc.nextInt();
        if (input == 1)
            handleNumber1();
        else if (input == 2)
            handleNumber2();
    }

    private static void handleNumber1() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the word:");
        String word = sc.nextLine().toUpperCase();
        System.out.println(mapOfWords.get(word));
    }

    private static void handleNumber2() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter a series of words:");
        String[] words = sc.nextLine().toLowerCase().split(" ");
        ArrayList<String> neutrals = new ArrayList<>();
        ArrayList<String> positives = new ArrayList<>();
        ArrayList<String> negatives = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            if (words[i].startsWith("+"))
                positives.add(words[i]);
            else if (words[i].startsWith("-"))
                negatives.add(words[i]);
            else neutrals.add(words[i]);
        }
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < neutrals.size(); i++){
            if (mapOfWords.get(neutrals.get(i)) == null){
                result = null;
                break;
            } else {
                addToResult(result, mapOfWords.get(neutrals.get(i)));
                if (result.isEmpty())
                    break;
            }
        }
    }

    private static void addToResult(ArrayList<Integer> result, ArrayList<Integer> addee) {
        //for (int i = 0; i < result.size(); i++){
        //    if (addee.contains(result.get(i)))
      //  }
    }

    private static void readAllFilesAndSaveWords() {
        mapOfWords = new HashMap<>();
        for (int i = 1; i <= 1000; i++) {
            try {
                Scanner sc = new Scanner(new FileReader("src/resources/file (" + i + ")"));
                while (sc.hasNext()) {
                    String word = sc.next().toUpperCase();
                    if (mapOfWords.get(word) != null) {
                        if (!mapOfWords.get(word).contains(i))
                            mapOfWords.get(word).add(i);
                    } else {
                        if (!allStopWords.contains(word)) {
                            ArrayList<Integer> temp = new ArrayList<>();
                            temp.add(i);
                            mapOfWords.put(word, temp);
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void readStopWords() {
        allStopWords = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/stop mapOfWords/stop_words_english.txt"));
            String word;
            while ((word = br.readLine()) != null) {
                allStopWords.add(word.toUpperCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}