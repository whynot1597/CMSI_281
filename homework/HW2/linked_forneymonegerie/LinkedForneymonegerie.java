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
        	
        	if (!(current.next != null)) {
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
        		size--;
        		modCount++;
        		if (current.count == 0) {
        			if (current == head) {
        				typeSize--;
            			head = current.next;
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
    	ForneymonType current = head;
        LinkedForneymonegerie clone = new LinkedForneymonegerie();
        clone.head = head;
        
        for (int i = 0; i < typeSize; i++) {
        	for (int j = 0; j < current.count; j++) {
        		clone.collect(current.type);
        	}
        	current = current.next;
        }
        
        return clone;
    }
    
    public void trade (LinkedForneymonegerie other) {
        ForneymonType tempHead = head;
        head = other.head;
        other.head = tempHead;
    }
    
    public LinkedForneymonegerie.Iterator getIterator () {
        throw new UnsupportedOperationException();
    }
    
    
    // -----------------------------------------------------------
    // Static methods
    // -----------------------------------------------------------
    
    public static LinkedForneymonegerie diffMon (LinkedForneymonegerie y1, LinkedForneymonegerie y2) {
        throw new UnsupportedOperationException();
    }
    
    public static boolean sameCollection (LinkedForneymonegerie y1, LinkedForneymonegerie y2) {
        throw new UnsupportedOperationException();
    }
    
    // Private helper methods
    // -----------------------------------------------------------

    // TODO: Your helper methods here!
    
    
    // Inner Classes
    // -----------------------------------------------------------
    
    public class Iterator implements LinkedForneymonegerieIteratorInterface {
        LinkedForneymonegerie owner;
        ForneymonType current;
        int itModCount;
        
        Iterator (LinkedForneymonegerie y) {
            // TODO
        }
        
        public boolean hasNext () {
            throw new UnsupportedOperationException();
        }
        
        public boolean hasPrev () {
            throw new UnsupportedOperationException();
        }
        
        public boolean isValid () {
            throw new UnsupportedOperationException();
        }
        
        public String getType () {
            throw new UnsupportedOperationException();
        }

        public void next () {
            throw new UnsupportedOperationException();
        }
        
        public void prev () {
            throw new UnsupportedOperationException();
        }
        
        public void replaceAll (String toReplaceWith) {
            throw new UnsupportedOperationException();
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
