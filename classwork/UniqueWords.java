/*
* Name: UniqueWords
* Authors: Patrick Utz / Jeremy Goldberg / Matt
* Date: 9/6/2018
* Description: Tells the user how many unique words are in a given sentence
*/

package words;

import java.util.Scanner;
import java.util.Arrays;  
import java.util.List;  
import java.util.ArrayList;


public class UniqueWords {
    
    public static void main(String[] args) {
        
        String[] originalInputArray;
        List<String> originalList;
        List<String> uniqueList;
        String originalInput;
        String lowercaseInput;
        Scanner keyboardInput;
        
        // Read input from user and initialize string array
        System.out.println("Welcome to Unique Words Finder!");
        System.out.print("Type in a sentence: ");
        
        keyboardInput = new Scanner(System.in);
        originalInput = keyboardInput.nextLine();
        keyboardInput.close();
        lowercaseInput = originalInput.toLowerCase();
        originalInputArray = lowercaseInput.split("\\s+");
        
        // Initialize array lists
        originalList = Arrays.asList(originalInputArray);
        uniqueList = new ArrayList<String>();
        
        // Check for duplicates
        for (int i = 0; i < originalList.size(); i++) {
            if (!(uniqueList.contains(originalList.get(i)))) {
                uniqueList.add(originalList.get(i));
            }
        }
        
        // Output result
        System.out.println("There are " + uniqueList.size() + " unique words in that sentence.");
    }

}
