import java.util.ArrayList;

public class Practice1 {
    public static void starter() {
        System.out.println("Please enter the word:");
        String wordInput = Main.sc.nextLine().toUpperCase().replaceAll("\\p{Punct}", "");
        ArrayList<Integer> result = Main.mapOfWords.get(wordInput);
        if (result == null) {
            System.out.println("No file found!");
        } else
            System.out.println(result);
    }
}
