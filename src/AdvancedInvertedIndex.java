import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class AdvancedInvertedIndex {
    public static void starter() {
        System.out.println("Please enter a series of words:");
        String[] words = ProgramController.sc.nextLine().toUpperCase().split(" ");
        Set<String> neutrals = new HashSet<>();
        Set<String> positives = new HashSet<>();
        Set<String> negatives = new HashSet<>();
        initializeSets(words, neutrals, positives, negatives); // Dividing words into 3 groups
        if (findTheResult(neutrals, positives, negatives).isEmpty())
            System.out.println("No file found!");
        else System.out.println(findTheResult(neutrals, positives, negatives));
    }

    private static void initializeSets(String[] words, Set<String> neutrals, Set<String> positives, Set<String> negatives) {
        for (String word : words) {
            if (word.startsWith("+"))
                positives.add(word.replaceFirst("\\+", ""));
            else if (word.startsWith("-"))
                negatives.add(word.replaceFirst("-", ""));
            else neutrals.add(word);
        }
    }

    private static Set<Integer> findTheResult(Set<String> neutrals, Set<String> positives, Set<String> negatives) {
        if (neutrals.isEmpty())
            return getArraysSub(findUnion(positives), findUnion(negatives));
        else if (!positives.isEmpty())
            return getArraysSub(getArraysCommonElements(findIntersection(neutrals), findUnion(positives)), findUnion(negatives));
        else
            return getArraysSub(findIntersection(neutrals), findUnion(negatives));
    }

    private static Set<Integer> findUnion(Set<String> arrayList) {
        Set<Integer> unionOfAll = new HashSet<>();
        for (String str : arrayList) {
            if (ProgramController.mapOfWords.containsKey(str))
                unionOfAll.addAll(ProgramController.mapOfWords.get(str));
        }
        return unionOfAll;
    }

    private static Set<Integer> findIntersection(Set<String> neutrals) {//*
        Set<Integer> intersection = new HashSet<>();
        List<String> stringsList = new ArrayList<>(neutrals);
        if (ProgramController.mapOfWords.containsKey(stringsList.get(0)))
            intersection.addAll(ProgramController.mapOfWords.get(stringsList.get(0)));
        for (String neutral : neutrals) {
            if (!ProgramController.mapOfWords.containsKey(neutral)) {
                intersection.clear();
                break;
            } else {
                intersection.retainAll(ProgramController.mapOfWords.get(neutral));
                if (intersection.isEmpty())
                    break;
            }
        }
        return intersection;
    }

    private static Set<Integer> getArraysCommonElements(Set<Integer> first, Set<Integer> second) {
        Set<Integer> result = new HashSet<>(first);
        result.retainAll(second);
        return result;
    }

    private static Set<Integer> getArraysSub(Set<Integer> first, Set<Integer> second) {
        Set<Integer> result = new HashSet<>(first);
        result.removeAll(second);
        return result;
    }
}