package sentinal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Sentinal implements SentinalInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------

    private PhraseHash posHash, negHash;


    // -----------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------

    Sentinal (String posFile, String negFile) throws FileNotFoundException {
    	posHash = new PhraseHash();
    	negHash = new PhraseHash();
    	loadSentimentFile(posFile, true);
        loadSentimentFile(negFile, false);
    }


    // -----------------------------------------------------------
    // Methods
    // -----------------------------------------------------------

    public void loadSentiment (String phrase, boolean positive) {
        if (positive) {
        	posHash.put(phrase);
        	return;
        }
        negHash.put(phrase);
    }

    public void loadSentimentFile (String filename, boolean positive) throws FileNotFoundException {
        Scanner reader = new Scanner(new File (filename));
        while (reader.hasNextLine()) {
        	loadSentiment(reader.nextLine(), positive);
        }
        reader.close();
    }

    public String sentinalyze (String filename) throws FileNotFoundException {
        int count = count(filename, true) - count(filename, false);
        if ( count != 0 ) {
        	return count > 0 ? "positive" : "negative"; 
        }
        return "neutral";
    }
    
    private int count (String filename, boolean positive) throws FileNotFoundException {
    	int count = 0;
    	PhraseHash hash = positive ? posHash : negHash;
    	Scanner reader = new Scanner(new File(filename));
    	for (int index = 0; reader.hasNext(); index++, reader.next()) {
    		Scanner innerReader = new Scanner(new File(filename));
    		for (int s = 0; s < index; s++) {
    			innerReader.next();
    		}
    		String test = "";
    		for (int i = 0; i < hash.longestLength() && innerReader.hasNext(); i++) {
    			if (test.equals("")) {
    				test = innerReader.next();
    			} else {
    				test = test + " " + innerReader.next();
    			}
    			if (hash.get(test) != null) {
    				count++;
    			}
    		}
    		innerReader.close();
    	}
    	reader.close();
    	return count;
    }
}
