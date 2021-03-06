/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  PhraseHash.java
 * @author    :  Andrew Forney (prototype)
 * @author    :  Jeremy Goldberg, Matt Stein, Patrick Utz
 * Date       :  2018-12-14
 * Description:  @see <a href='http://forns.lmu.build/classes/fall-2018/cmsi-281/homework/hw5/homework-5.html'>Assignment Page</a>
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

package sentinal;

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
        if (buckets[index] == null) {
        	String[] wordCount = s.split("\\s+");
            if (wordCount.length > longest) {
            	longest = wordCount.length;
            }
            buckets[index] = s;
            size++;
        } 
        checkAndGrow();
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
