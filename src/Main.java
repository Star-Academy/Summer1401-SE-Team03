import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    static ArrayList<String> allStopWords;
    static HashMap<String, ArrayList<Integer>> mapOfWords;

    public static void main(String[] args) {
        readAndSaveStopWords();
        readAllFilesAndSaveWords();
        handleInput();
    }

    private static void handleInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the number of exercise: 1 or 2?");
        int input = sc.nextInt();
        if (input == 1)
            exercise1();
        else if (input == 2)
            exercise2();
    }

    private static void exercise1() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the word:");
        String word = sc.nextLine().toUpperCase().replaceAll("\\p{Punct}", "");
        ArrayList<Integer> result = mapOfWords.get(word);
        if (result == null)
            System.out.println("No file found!");
        else
            System.out.println(result);
    }

    private static void exercise2() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter a series of words:");
        String[] words = sc.nextLine().toUpperCase().split(" ");

        ArrayList<String> neutrals = new ArrayList<>();
        ArrayList<String> positives = new ArrayList<>();
        ArrayList<String> negatives = new ArrayList<>();

        initializeArrayLists(words, neutrals, positives, negatives); // Dividing words into 3 groups
        System.out.println(findTheResult(neutrals, positives, negatives));
    }

    private static void initializeArrayLists(String[] words, ArrayList<String> neutrals, ArrayList<String> positives, ArrayList<String> negatives) {
        for (String word : words) {
            if (word.startsWith("+"))
                positives.add(word.replaceFirst("\\+", ""));
            else if (word.startsWith("-"))
                negatives.add(word.replaceFirst("-", ""));
            else neutrals.add(word);
        }
    }

    private static ArrayList<Integer> findTheResult(ArrayList<String> neutrals, ArrayList<String> positives, ArrayList<String> negatives) {
        ArrayList<Integer> result;
        ArrayList<Integer> intersectionOfNeutrals = findIntersection(neutrals);
        ArrayList<Integer> unionOfPositives = findUnion(positives);
        ArrayList<Integer> unionOfNegatives = findUnion(negatives);

        if (neutrals.isEmpty()) {
            result = getArraysSub(unionOfPositives, unionOfNegatives);
        } else {
            if (!positives.isEmpty()) {
                result = getArraysCommonElements(intersectionOfNeutrals, unionOfPositives);
                result = getArraysSub(result, unionOfNegatives);
            } else {
                result = getArraysSub(intersectionOfNeutrals, unionOfNegatives);
            }
        }
        return result;
    }

    private static ArrayList<Integer> findUnion(ArrayList<String> arrayList) {
        ArrayList<Integer> unionOfAll = new ArrayList<>();
        for (String str : arrayList) {
            if (mapOfWords.containsKey(str)) {
                unionOfAll = getArraysUnion(unionOfAll, mapOfWords.get(str));
            }
        }
        return unionOfAll;
    }

    private static ArrayList<Integer> findIntersection(ArrayList<String> neutrals) {
        ArrayList<Integer> intersection = new ArrayList<>();
        boolean isFirstTime = true;
        for (String neutral : neutrals) {
            if (!mapOfWords.containsKey(neutral)) {
                intersection.clear();
                break;
            } else {
                if (isFirstTime) {
                    intersection.addAll(mapOfWords.get(neutral));
                    isFirstTime = false;
                } else {
                    intersection = getArraysCommonElements(intersection, mapOfWords.get(neutral));
                    if (intersection.isEmpty())
                        break;
                }
            }
        }
        return intersection;
    }

    // This method returns the intersection of first and second. first = A, second = B. The method returns A n B
    private static ArrayList<Integer> getArraysCommonElements(ArrayList<Integer> first, ArrayList<Integer> second) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int fileNumber : first) {
            if (second.contains(fileNumber))
                result.add(fileNumber);
        }
        return result;
    }

    // This method returns the union of first and second. first = A, second = B. The method returns A u B
    private static ArrayList<Integer> getArraysUnion(ArrayList<Integer> first, ArrayList<Integer> second) {
        ArrayList<Integer> result = new ArrayList<>(first);
        for (Integer fileNumber : second)
            if (!result.contains(fileNumber))
                result.add(fileNumber);
        return result;
    }

    // This method subtracts the second set from the first one. first = A, second = B. The method returns A - B
    private static ArrayList<Integer> getArraysSub(ArrayList<Integer> first, ArrayList<Integer> second) {
        ArrayList<Integer> result = new ArrayList<>();
        for (Integer fileNumber : first) {
            if (!second.contains(fileNumber))
                result.add(fileNumber);
        }
        return result;
    }

    // All files are in the "resources" folder. Their names are like: file (#fileNumber).
    // This method reads the words in those files and saves them in a hashmap called mapOfWords.
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

    // All the stop words are in the file "stop_words_english.txt".
    // This method reads them and saves them in an arraylist called allStopWords.
    private static void readAndSaveStopWords() {
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