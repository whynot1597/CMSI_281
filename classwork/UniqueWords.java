import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UniqueWords {
	
    public static int countUniqueWords(String str) {
		
        String[] words;
        
        List<String> wordBank = new ArrayList<>();
		
        words = str.split("\\s+");
        
        for (int i = 0; i < words.length; i++) {
            if (wordBank.size() == 0) {
                wordBank.add(words[i]);
            }
            for (int j = 0; j < wordBank.size(); j++) {
	            if (words[i].equals(wordBank.get(j))) {
			        break;
			    }
			    
			    if (j == wordBank.size() - 1) {
			        wordBank.add(words[i]);
			    }
            }
        }
        
        return wordBank.size();
    }
    
    public static void main(String [] args) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Feed me a sentence:");
        String sentence = keyboard.nextLine();
        keyboard.close();

        System.out.println("This sentence has " + countUniqueWords(sentence) + "unique words!");
     }

}
