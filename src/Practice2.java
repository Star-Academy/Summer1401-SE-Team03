import java.util.ArrayList;

public class Practice2 {
    public static void starter() {
        System.out.println("Please enter a series of words:");
        String[] words = Main.sc.nextLine().toUpperCase().split(" ");

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
            if (Main.mapOfWords.containsKey(str)) {
                unionOfAll = getArraysUnion(unionOfAll, Main.mapOfWords.get(str));
            }
        }
        return unionOfAll;
    }

    private static ArrayList<Integer> findIntersection(ArrayList<String> neutrals) {
        ArrayList<Integer> intersection = new ArrayList<>();
        boolean isFirstTime = true;
        for (String neutral : neutrals) {
            if (!Main.mapOfWords.containsKey(neutral)) {
                intersection.clear();
                break;
            } else {
                if (isFirstTime) {
                    intersection.addAll(Main.mapOfWords.get(neutral));
                    isFirstTime = false;
                } else {
                    intersection = getArraysCommonElements(intersection, Main.mapOfWords.get(neutral));
                    if (intersection.isEmpty())
                        break;
                }
            }
        }
        return intersection;
    }

    private static ArrayList<Integer> getArraysCommonElements(ArrayList<Integer> first, ArrayList<Integer> second) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int fileNumber : first) {
            if (second.contains(fileNumber))
                result.add(fileNumber);
        }
        return result;
    }

    private static ArrayList<Integer> getArraysUnion(ArrayList<Integer> first, ArrayList<Integer> second) {
        ArrayList<Integer> result = new ArrayList<>(first);
        for (Integer fileNumber : second)
            if (!result.contains(fileNumber))
                result.add(fileNumber);
        return result;
    }

    private static ArrayList<Integer> getArraysSub(ArrayList<Integer> first, ArrayList<Integer> second) {
        ArrayList<Integer> result = new ArrayList<>();
        for (Integer fileNumber : first) {
            if (!second.contains(fileNumber))
                result.add(fileNumber);
        }
        return result;
    }

}
