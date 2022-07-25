import java.util.ArrayList;
public class SimpleInvertedIndex {
    public static void starter() {
        System.out.println("Please enter the word:");
        String wordInput = ProgramController.sc.nextLine().toUpperCase().replaceAll("\\p{Punct}", "");
        ArrayList<Integer> result = ProgramController.mapOfWords.get(wordInput);
        if (result == null)
            System.out.println("No file found!");
        else
            System.out.println(result);
    }
}