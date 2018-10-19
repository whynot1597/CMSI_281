/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  LinkedForneymonegerie.java
 * Purpose    :  A Forneymonegerie is a collection of Strings in which duplicates indicate possession of 
 *               multiple Forneymon of the same type, but the only ordering that must be recorded is the 
 *               order in which a ForneymonType is collected. A Forneymonegerie thus maps types (Strings) 
 *               to the number (ints) of individual Forneymon belonging to each type.
 * @author    :  Andrew Forney (prototype)
 * @author    :  Jeremy Goldberg
 * Date       :  2018-10-19
 * Description:  @see <a href='http://forns.lmu.build/classes/fall-2018/cmsi-281/homework/hw1/homework-2.html'>Assignment Page</a>
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

package linked_forneymonegerie;

import java.util.NoSuchElementException;

public class LinkedForneymonegerie implements LinkedForneymonegerieInterface {

    // Fields
    // -----------------------------------------------------------
    private ForneymonType head;
    private int size, typeSize, modCount;
    
    
    // Constructor
    // -----------------------------------------------------------
    LinkedForneymonegerie () {
        head = null;
        size = 0;
        typeSize = 0;
        modCount = 0;
    }
    
    
    // Methods
    // -----------------------------------------------------------
    public boolean empty () {
        return size == 0;
    }
    
    public int size () {
        return size;
    }
    
    public int typeSize () {
        return typeSize;
    }
    
    public boolean collect (String toAdd) {
        if (size == 0) {
        	head = new ForneymonType(toAdd, 1);
        	size++;
        	typeSize++;
        	modCount++;
        	head.next = null;
        	head.prev = null;
        	return true;
        }
        
        ForneymonType current = head;        
        for (int i = 0; i < typeSize; i++) {
        	if (current.type.equals(toAdd)) {
        		current.count++;
        		size++;
        		modCount++;
        		return false;
        	}
        	
        	if (current.next == null) {
        		current.next = new ForneymonType(toAdd, 1);
        		current.next.prev = current;
        		current.next.next = null;
        		size++;
                typeSize++;
                modCount++;
                return true;
        	}
        	current = current.next;
        }
        
        return false;
    }
    
    public boolean release (String toRemove) {
        ForneymonType current = head;        
        for (int i = 0; i < typeSize; i++) {
        	if (current.type.equals(toRemove)) {
        		current.count--;
        		size--;
        		modCount++;
        		if (current.count == 0) {
        			if (current == head) {
        				typeSize--;
        				if (typeSize == 0) {
        					head = null;
        					return true;
        				}
            			head = current.next;
            			head.prev = null;
            			return true;
            		}
        			current.prev.next = current.next;
        			typeSize--;
        			return true;
        		}
        		return true;
        	}
        	current = current.next;
        }
        return false;
    }
    
    public void releaseType (String toNuke) {
        ForneymonType current = head;
        for (int i = 0; i < typeSize; i++) {
        	if (current.type.equals(toNuke)) {
        		size -= current.count;
        		modCount++;
        		typeSize--;
        		if (current == head) {
        			head = current.next;
        			if (head == null) {
        				return;
        			}
        			head.prev = null;
        			return;
        		}
        		current.prev.next = current.next;
        		return;
        	}
        	current = current.next;
        }
    }
    
    public int countType (String toCount) {
        ForneymonType current = head;
        for (int i = 0; i < typeSize; i++) {
        	if (current.type.equals(toCount)) {
        		return current.count;
        	}
        	current = current.next;
        }
        
        return 0;
    }
    
    public boolean contains (String toCheck) {
        ForneymonType current = head;
        for (int i = 0; i < typeSize; i++) {
        	if (current.type.equals(toCheck)) {
        		return true;
        	}
        	current = current.next;
        }
        return false;
    }
    
    public String rarestType () {
    	if (size == 0) {
    		return null;
    	}
    	ForneymonType current = head;
    	ForneymonType rarest = current;
    	
    	for (int i = 0; i < typeSize; i++) {
    		if (current.count <= rarest.count) {
    			rarest = current;
    		}
    		current = current.next;
    	}
    	
    	return rarest.type;
    }
    
    public LinkedForneymonegerie clone () {
    	LinkedForneymonegerie newFm = new LinkedForneymonegerie();
        
        collectAll(this, newFm);
        
        return newFm;
    }
    
    public void trade (LinkedForneymonegerie other) {
    	LinkedForneymonegerie tempFm = this.clone();
        
        this.reset();
        collectAll(other, this);
        
        other.reset();
        collectAll(tempFm, other);
    }
    
    public LinkedForneymonegerie.Iterator getIterator () {
        return new LinkedForneymonegerie.Iterator(this);
    }
    
    @Override
    public String toString() {
    	String result = "[ ";
    	ForneymonType current = head;
    	for (int i = 0; i < typeSize - 1; i++) {
    		result = result + "\"" + current.type + "\"" + ": " + current.count + ", ";
    		current = current.next;
    	}
    	return result + "\"" + current.type + "\"" + ": " + current.count + " ]";
    }
    
    // -----------------------------------------------------------
    // Static methods
    // -----------------------------------------------------------
    
    public static LinkedForneymonegerie diffMon (LinkedForneymonegerie y1, LinkedForneymonegerie y2) {
    	LinkedForneymonegerie tempFm = y1.clone();
    	ForneymonType current = y2.head;
        
        for (int i = 0; i < y2.typeSize; i++) {
            if (tempFm.contains(current.type)) {
                int amount = current.count;
                for (int j = 0; j < amount; j++) {
                    tempFm.release(current.type);
                }
            }
            current = current.next;
        }
        
        return tempFm;
    }
    
    public static boolean sameCollection (LinkedForneymonegerie y1, LinkedForneymonegerie y2) {
        return (diffMon(y1, y2).size == 0) && (diffMon(y2, y1).size == 0);
    }
    
    private static void collectAll(LinkedForneymonegerie source, LinkedForneymonegerie target) {
    	ForneymonType current = source.head;
    	for (int i = 0; i < source.typeSize; i++) {
        	for (int j = 0; j < current.count; j++) {
        		target.collect(current.type);
        	}
        	current = current.next;
        }
    }
    
    // Private helper methods
    // -----------------------------------------------------------

    private void reset() {
        size = 0;
        typeSize = 0;
        head = null;
        modCount = 0;
    }
    
    
    // Inner Classes
    // -----------------------------------------------------------
    
    public class Iterator implements LinkedForneymonegerieIteratorInterface {
        LinkedForneymonegerie owner;
        ForneymonType current;
        int currentIndex;
        int itModCount;
        
        Iterator (LinkedForneymonegerie y) {
            itModCount = y.modCount;
            current = y.head;
            owner = y;
            currentIndex = 0;
        }
        
        public boolean hasNext () {
            return (current.next != null) || (currentIndex < current.count - 1);
        }
        
        public boolean hasPrev () {
            return (current.prev != null) || (currentIndex > 0);
        }
        
        public boolean isValid () {
        	return itModCount == owner.modCount;
        }
        
        public String getType () {
            return current.type;
        }

        public void next () {
        	if (!isValid()) {
        		throw new IllegalStateException();
        	}
        	if (!hasNext()) {
        		throw new NoSuchElementException();
        	}
        	if (currentIndex < current.count - 1) {
        		currentIndex++;
        		return;
        	}
        	currentIndex = 0;
            current = current.next;
        }
        
        public void prev () {
        	if (!isValid()) {
        		throw new IllegalStateException();
        	}
        	if (!hasPrev()) {
        		throw new NoSuchElementException();
        	}
        	if (currentIndex > 0) {
        		currentIndex--;
        		return;
        	}
        	current = current.prev;
        	if (current != null) {
        		currentIndex = current.count - 1;
        	}
        }
        
        public void replaceAll (String toReplaceWith) {
        	if (!isValid()) {
        		throw new IllegalStateException();
        	}
        	if (current == null) {
        		return;
        	}
        	if (current.type.equals(toReplaceWith)) {
        		return;
        	}
        	
        	int amount = current.count;
        	if (owner.contains(toReplaceWith)) {
        		
        		addAndReplace(toReplaceWith, amount);
        		
        		current = head;
        		while (!toReplaceWith.equals(current.type)) {
        			current = current.next;
        		}
        		return;
        	}
        	
        	addAndReplace(toReplaceWith, amount);
        	
        	current = head;
        	for (int i = 0; i < owner.typeSize - 1; i++) {
        		current = current.next;
        	}
        }
        
        private void addAndReplace(String toReplaceWith, int amount) {
        	for (int i = 0; i < amount; i++) {
    			owner.collect(toReplaceWith);
    			itModCount++;
    		}
    		owner.releaseType(current.type);
    		itModCount++;
        }
        
    }
    
    private class ForneymonType {
        ForneymonType next, prev;
        String type;
        int count;
        
        ForneymonType (String t, int c) {
            type = t;
            count = c;
        }
    }
    
}
