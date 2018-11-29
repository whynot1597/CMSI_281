/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  Dictreenary.java
 * Purpose    :  Provides framework for creating a tree with words that can be added, checked, spell checked
 * 				 or get a sorted list of all the words
 * @author    :  Andrew Forney (prototype)
 * @author    :  Jeremy Goldberg
 * Date       :  2018-11-29
 * Description:  @see <a href='http://forns.lmu.build/classes/fall-2018/cmsi-281/homework/hw4/homework-4.html'>Assignment Page</a>
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


package dictreenary;

import java.util.ArrayList;

public class Dictreenary implements DictreenaryInterface {

    // Fields
    // -----------------------------------------------------------
    TTNode root;
    
    
    // Constructor
    // -----------------------------------------------------------
    Dictreenary () {}
    
    
    // Methods
    // -----------------------------------------------------------
    
    public boolean isEmpty () {
        return root == null;
    }
    
    public void addWord (String toAdd) {
    	//Edge Case: user inputs nothing
    	if (toAdd == null) {
    		return;
    	}
    	toAdd = normalizeWord(toAdd);
    	char[] toAddArray = toAdd.toCharArray();
        
    	// Edge Case: there is nothing in the Dictreenary
        if (isEmpty()) {
        	root = new TTNode(toAddArray[0], false);
        	addRemainingChars(toAddArray, 1, root);
        	return;
        }
        
        TTNode current = root;
        
        for (int i = 0; i < toAddArray.length; i++) {
        	current = findNextLetter(toAddArray[i], current, true);  //Adds next letter in correct spot and returns that spot
        	//If there is nothing below it we can add the rest of the word
        	if (current.mid == null) {
        		addRemainingChars(toAddArray, i + 1, current);
        		return;
        	}
        	//If the letter we added is the last one
        	if (i + 1 == toAddArray.length) {
        		current.wordEnd = true;
        		return;
        	}
        	current = current.mid;
        }
        
        return;
    }
    
    public boolean hasWord (String query) {
    	//Edge Case: user does not input anything or there is nothing in the dictreenary
    	if (query == null || isEmpty()) {
    		return false;
    	}
    	
        String normalized = normalizeWord(query);
        char[] queryArray = normalized.toCharArray();
        
        TTNode current = root;
        TTNode previous = null;
        for (int i = 0; i < queryArray.length; i++) {
        	current = findNextLetter(queryArray[i], current, false); //Returns the space where the letter is or null
        	if (current == null) {
        		return false;
        	}
        	previous = current;
        	current = current.mid;
        }
        return previous.wordEnd;
    }
    
    public String spellCheck (String query) {
        if (query == null || isEmpty()) { 
        	return null;
        }
        
        String normalized = normalizeWord(query);
        char[] queryArray = normalized.toCharArray();
        
        if (hasWord(normalized)) {
        	return normalized;
        }
        
        for (int i = 0; i < queryArray.length - 1; i++) {
        	String switchedQuery = new String(swapNext(queryArray, i));
        	if (hasWord(switchedQuery)) {
        		return switchedQuery;
        	}
        	swapNext(queryArray, i);
        }
        return null;
    }
    
    public ArrayList<String> getSortedWords () {
        if (isEmpty()) {
        	return null;
        }
        
        return getWordsFrom(root, "");
    }
    
    
    // Helper Methods
    // -----------------------------------------------------------
    
    private String normalizeWord (String s) {
        // Edge case handling: empty Strings illegal
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        return s.trim().toLowerCase();
    }
    
    /*
     * Returns:
     *   int less than 0 if c1 is alphabetically less than c2
     *   0 if c1 is equal to c2
     *   int greater than 0 if c1 is alphabetically greater than c2
     */
    private int compareChars (char c1, char c2) {
        return Character.toLowerCase(c1) - Character.toLowerCase(c2);
    }
    
    
    //Adds remaining chars of given char[] to dictreenary
    private void addRemainingChars(char[] wordArray, int startIndex, TTNode currentNode) {
    	
    	for (int i = startIndex; i < wordArray.length; i++) {
    		currentNode.mid = new TTNode(wordArray[i], false);
    		currentNode = currentNode.mid;
    	}
    	currentNode.wordEnd = true;
    }
    
    //Returns the location of c, adds if need too and add == true
    private TTNode findNextLetter (char c, TTNode currentNode, boolean add) { 
    	if (currentNode == null) {
    		return null;
    	}
    	int compare = compareChars(c, currentNode.letter);
    	
    	//case: this is the correct node
    	if (compare == 0) {
    		return currentNode;
    	}
    	//case: the correct node is to the left
    	if (compare < 0) {
    		if (currentNode.left == null && add) {
    			currentNode.left = new TTNode(c, false);
        		return currentNode.left;
    		}
    		return findNextLetter(c, currentNode.left, add);
    	}
    	//case: the correct node is to the right
    	if (compare > 0) {
    		if (currentNode.right == null && add) {
    			currentNode.right = new TTNode(c, false);
        		return currentNode.right;
    		}
    		return findNextLetter(c, currentNode.right, add);
    	}
    	return null;
    }
   
    private char[] swapNext (char[] charArray, int index) {
    	char temp = charArray[index];
    	charArray[index] = charArray[index + 1];
    	charArray[index + 1] = temp;
    	return charArray;
    }
    
    private ArrayList<String> getWordsFrom (TTNode current, String currentWord) {
    	ArrayList<String> result = new ArrayList<String>();
    	if (current == null) {
    		return result;
    	}

    	result.addAll(getWordsFrom(current.left, currentWord));
    	if (current.wordEnd) {
    		result.add(currentWord + current.letter);
    	}
    	result.addAll(getWordsFrom(current.mid, currentWord + current.letter));
    	result.addAll(getWordsFrom(current.right, currentWord));   
    	
    	return result;
    }    
    
    // TTNode Internal Storage
    // -----------------------------------------------------------
    
    /*
     * Internal storage of Dictreenary words
     * as represented using a Ternary Tree with TTNodes
     */
    private class TTNode {
        
        boolean wordEnd;
        char letter;
        TTNode left, mid, right;
        
        TTNode (char c, boolean w) {
            letter  = c;
            wordEnd = w;
        }
        
    }
    
}
