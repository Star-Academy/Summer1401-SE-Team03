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
// meow
//[704, 706, 783, 784, 785]

    // cat
//[96, 244, 357, 366, 393, 614, 627, 664, 703, 704, 706, 722, 738, 783, 784, 785, 870, 880, 926, 944]
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
        String word = sc.nextLine().toUpperCase().replaceAll("\\p{Punct}", "");
        System.out.println(mapOfWords.get(word));
    }

    private static void handleNumber2() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter a series of words:");
        String[] words = sc.nextLine().toUpperCase().split(" ");

        ArrayList<String> neutrals = new ArrayList<>();
        ArrayList<String> positives = new ArrayList<>();
        ArrayList<String> negatives = new ArrayList<>();

        for (String word : words) { // Dividing words into 3 groups
            if (word.startsWith("+"))
                positives.add(word.replaceFirst("\\+", ""));
            else if (word.startsWith("-"))
                negatives.add(word.replaceFirst("-", ""));
            else neutrals.add(word);
        }
        System.out.println(findingTheResult(neutrals, positives, negatives));
    }

    private static ArrayList<Integer> findingTheResult(ArrayList<String> neutrals, ArrayList<String> positives, ArrayList<String> negatives) {
        ArrayList<Integer> result = new ArrayList<>();
        boolean isFirstTime = true;
        for (String neutral : neutrals) {
            if (!mapOfWords.containsKey(neutral)) {
                result.clear();
                break;
            } else {
                if (isFirstTime) {
                    result = mapOfWords.get(neutral);
                    isFirstTime = false;
                } else {
                    result = getIntersectionOverArrayLists(result, mapOfWords.get(neutral));
                    if (result.isEmpty())
                        break;
                }
            }
        }
        // So far, we have found the intersection of neutrals!

        ArrayList<Integer> unionOfPositives = new ArrayList<>();
        ArrayList<Integer> unionOfNegatives = new ArrayList<>();
        for (String positive : positives) { // finding the union of positives
            if (mapOfWords.containsKey(positive)) {
                unionOfPositives = getUnionOverArrayLists(unionOfPositives, mapOfWords.get(positive));
            }
        }
        for (String negative : negatives) { // finding the union of negatives
            if (mapOfWords.containsKey(negative)) {
                unionOfNegatives = getUnionOverArrayLists(unionOfNegatives, mapOfWords.get(negative));
            }
        }

        if (neutrals.isEmpty()) {
            result = getSubtractionFromArrayLists(unionOfPositives, unionOfNegatives);
        } else {
            if (!positives.isEmpty()) {
                result = getIntersectionOverArrayLists(result, unionOfPositives);
            }
            result = getSubtractionFromArrayLists(result, unionOfNegatives);
        }
        return result;
    }

    private static ArrayList<Integer> getIntersectionOverArrayLists(ArrayList<Integer> first, ArrayList<Integer> second) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int fileNumber : first) {
            if (second.contains(fileNumber))
                result.add(fileNumber);
        }
        return result;
    }

    private static ArrayList<Integer> getUnionOverArrayLists(ArrayList<Integer> first, ArrayList<Integer> second) {
        ArrayList<Integer> result = new ArrayList<>(first);
        for (Integer fileNumber : second)
            if (!result.contains(fileNumber))
                result.add(fileNumber);
        return result;
    }

    private static ArrayList<Integer> getSubtractionFromArrayLists(ArrayList<Integer> first, ArrayList<Integer> second) {
        ArrayList<Integer> result = new ArrayList<>();
        for (Integer fileNumber : first) {
            if (!second.contains(fileNumber))
                result.add(fileNumber);
        }
        return result;
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