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
    	if (toAdd == null) {
    		return;
    	}
    	toAdd = normalizeWord(toAdd);
    	char[] toAddArray = toAdd.toCharArray();
        
        if (isEmpty()) {
        	root = new TTNode(toAddArray[0], false);
        	addRemainingChars(toAddArray, 1, root);
        	return;
        }
        
        TTNode current = root;
        
        for (int i = 0; i < toAddArray.length; i++) {
        	current = findNextLetter(toAddArray[i], current, true);
        	if (current.mid == null) {
        		addRemainingChars(toAddArray, i + 1, current);
        		return;
        	}
        	if (i + 1 == toAddArray.length) {
        		current.wordEnd = true;
        		return;
        	}
        	current = current.mid;
        }
        
        return;
    }
    
    public boolean hasWord (String query) {
    	if (query == null || isEmpty()) {
    		return false;
    	}
    	
        String normalized = normalizeWord(query);
        char[] queryArray = normalized.toCharArray();
        
        TTNode current = root;
        TTNode previous = null;
        for (int i = 0; i < queryArray.length; i++) {
        	current = findNextLetter(queryArray[i], current, false);
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
    
    private void addRemainingChars(char[] wordArray, int startIndex, TTNode currentNode) {
    	
    	for (int i = startIndex; i < wordArray.length; i++) {
    		currentNode.mid = new TTNode(wordArray[i], false);
    		currentNode = currentNode.mid;
    	}
    	currentNode.wordEnd = true;
    }
    
    private TTNode findNextLetter (char c, TTNode currentNode, boolean add) { 
    	if (currentNode == null) {
    		return null;
    	}
    	int compare = compareChars(c, currentNode.letter);
    	
    	if (compare == 0) {
    		return currentNode;
    	}
    	if (compare < 0) {
    		if (currentNode.left == null && add) {
    			currentNode.left = new TTNode(c, false);
        		return currentNode.left;
    		}
    		return findNextLetter(c, currentNode.left, add);
    	}
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
    
    // [!] Add your own helper methods here!
    
    
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
