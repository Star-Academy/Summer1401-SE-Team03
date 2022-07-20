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
            handle_inout2();
    }

    private static void handleNumber1() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the word:");
        String word = sc.nextLine().toUpperCase().replaceAll("\\p{Punct}", "");
        if (mapOfWords.get(word) != null) {
            System.out.println(mapOfWords.get(word));
        } else {
            System.out.println("No file found");
        }
    }

    private static ArrayList<Integer> handleNumber2(String word) {
        return mapOfWords.get(word);
    }

    private static void handle_inout2() {
        String new_w = "";
        String new_w_2 = "";
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter a series of words:");
        String[] words = sc.nextLine().toUpperCase().split(" ");
        for (String w : words) {
            if (w.startsWith("-")) {
                new_w = w.replace("-", "");
            } else if (w.startsWith("+")) {
                new_w = w.replace("+", "");
            } else {
                new_w_2 = w;
                if (!result.contains(handleNumber2(new_w_2))) {
                    result.add(handleNumber2(new_w_2));
                    continue;
                }
            }
            if (!w.startsWith("-") && handleNumber2(new_w) != null && !result.contains(handleNumber2(new_w))) {
                result.add(handleNumber2(new_w));

            }
        }
        for (String w : words) {
            if (w.startsWith("-")) {
                new_w = w.replace("-", "");
                result.remove(handleNumber2(new_w));
            }
        }
        if (result.size() == 0) {
            System.out.println("No file found");
        } else {
            if (!new_w_2.equals("") && !result.contains(handleNumber2(new_w_2))) {
                result.remove(handleNumber2(new_w));
            } else {
                System.out.println(result);
            }
        }
    }

    private static void readAllFilesAndSaveWords() {
        mapOfWords = new HashMap<>();
        for (int i = 1; i <= 1000; i++) {
            try {
                Scanner sc = new Scanner(new FileReader("src/resources/file (" + i + ")"));
                while (sc.hasNext()) {
                    String word = sc.next().toUpperCase().replaceAll("\\p{Punct}", "");
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
            BufferedReader br = new BufferedReader(new FileReader("src/stop words/stop_words_english.txt"));
            String word;
            while ((word = br.readLine()) != null) {
                allStopWords.add(word.toUpperCase().replaceAll("\\p{Punct}", ""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}