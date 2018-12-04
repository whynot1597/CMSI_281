package sentinal;
 import sentinal.PhraseHash;
import sentinal.PhraseHashInterface;
 public class PhraseHash implements PhraseHashInterface {
     // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------
     private final static int BUCKET_START = 1000;
    private final static double LOAD_MAX = 0.7;
    private int size, longest;
    private String[] buckets;
     // -----------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------
     PhraseHash () {
        size = 0;
        longest = 0;
        buckets = new String[BUCKET_START];
    }
     // -----------------------------------------------------------
    // Public Methods
    // -----------------------------------------------------------
     public int size () {
        return size;
    }
     public boolean isEmpty () {
        return size == 0;
    }
     public void put (String s) {
    	if (s == null) {
    		return;
    	}
        int index = hash(s);
        checkAndGrow();
        if (buckets[index] == null) {
        	String[] wordCount = s.split("\\s+");
            if (wordCount.length > longest) {
            	longest = wordCount.length;
            }
            buckets[index] = s;
            size++;
        }        
    }
     public String get (String s) {
        int index = hash(s);
        if (index >= buckets.length || 
        		buckets[index] == null ||
        		!buckets[index].equals(s)) {
        	return null;
        }
        return s;
    }
     public int longestLength () {
        return longest;
    }
     // -----------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------
     private int hash (String s) {
    	int hashCode = 0;
    	byte[] strByte = s.getBytes();
        for (int i = 0; i < s.length(); i++) {
        	hashCode += Byte.toUnsignedInt(strByte[i]) * (i);
        }
        
        return hashCode % buckets.length;
    }
     private void checkAndGrow () {
        if (size / buckets.length > LOAD_MAX) {
        	String[] temp = buckets;
        	buckets = new String[temp.length * 2];
        	for (int i = 0; i < temp.length; i++) {
        		put(temp[i]);
        	}
        	buckets = temp;
        }
    }
}
  
58  homework/HW5/sentinal/Sentinal.java
@@ -0,0 +1,58 @@
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
        // TODO: load files into PhraseHash fields appropriately
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
        loadSentiment(reader.nextLine(), positive);
        reader.close();
    }
     public String sentinalyze (String filename) throws FileNotFoundException {
        throw new UnsupportedOperationException();
    }
     // -----------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------
     // TODO: add your helper methods here!
 }
