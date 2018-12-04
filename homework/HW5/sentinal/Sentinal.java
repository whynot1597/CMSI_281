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
        int totalCount = count(filename, true) - count(filename, false);
        if (totalCount > 0) {
        	return "positive";
        }
        if (totalCount < 0) {
        	return "negative";
        }
        return "neutral";
        
    }
    
    private int count(String filename, boolean positive) throws FileNotFoundException {
    	int count = 0;
    	PhraseHash hash;
    	if (positive) { 
    		hash = posHash;
    	} else {
    		hash = negHash;
    	}
    	Scanner reader = new Scanner(new File(filename));
    	int index = 0;
    	while (reader.hasNext()) {
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
    		index++;
    		reader.next();
    		innerReader.close();
    	}
    	reader.close();
    	return count;
    }
}
