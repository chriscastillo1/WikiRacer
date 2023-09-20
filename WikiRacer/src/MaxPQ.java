import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/*
 * TODO: This file should contain your priority queue backed by a binary
 * max-heap.
 */
public class MaxPQ {
	
	private int cap = 10;
	private int size;
	private LContainer[] pQ;
	
	/*
     * A public constructor that initializes the pQ to have
     * size = 0; capacity of 10; and a new array
     */
    public MaxPQ() {
        size = 0;
        pQ = new LContainer[cap];
    }
    
    public void enqueue(LContainer ladder) {
    	if (size + 1 >= cap) { doubleCap(); }
    	if (size == 0) { size++; pQ[size] = ladder; return; }
    	
    	size++;
    	pQ[size] = ladder;
    	bubbleUp(size, pIndex(size));
    }
    
    private void bubbleUp(int pos, int parent) {
    	if (pos == 1) { return; }
    	
    	if (pQ[pos].priority > pQ[parent].priority) {
    		swapContainer(pos, parent); pos = parent;
    		bubbleUp(pos, pIndex(pos));
    	}
    }
    
    public List<String> dequeue() {
    	if (size == 0) { throw new NoSuchElementException(); }
    	
    	List<String> ladderList = pQ[1].ladder;
    	pQ[1] = pQ[size];
    	size--;
    	bubbleDown(1);
    	return ladderList;
    }
    
    private void bubbleDown(int pos) {
    	if (pos*2 > size) { return; }
    	
    	int switchPos = compareLeftRight(pos);
    	if (pQ[pos].priority < pQ[switchPos].priority) {
    		swapContainer(pos, switchPos); pos = switchPos;
    		bubbleDown(pos);
    	} else {
    		return;
    	}
    }
    
    private int compareLeftRight(int pos) {
    	int left = left(pos);
    	int right = right(pos);
    	
    	if (left <= size && right > size) { return left; }
    	
    	if (pQ[left].priority == pQ[right].priority) {
    		return left;
    	}
    	
    	if (pQ[left].priority > pQ[right].priority) {
    		return left;
    	} else {
    		return right;
    	}
    }
    
    /*
     * An overloaded method that adds a ladder into the queue based on given priority
     * (NOTE: They will be bubbled up to keep appropriate heap order)
     */
    public void enqueue(List<String> ladder, int priority) {
    	LContainer newLadder = new LContainer(ladder, priority);
    	this.enqueue(newLadder);
    }
    
    /*
     * A method that returns the ladder of the front patient in the queue.
     * (NOTE: Throws NoSuchElementException if queue is empty)
     * 
     * Return Value: List<String> ladder (that contains links to wiki)
     */
    public List<String> peek() {
        if (size == 0) { throw new NoSuchElementException(); }
        return pQ[1].ladder;
    }
    
    /*
     * A method that returns the priority of the front ladder in the queue.
     * (NOTE: Throws No Element Exception if Queue is Empty)
     * 
     * Return Value: Priority of patient (frontmost in queue)
     */
    public int peekPriority() {
        if (this.isEmpty()) { throw new NoSuchElementException(); }
        return pQ[1].priority;
    }
    
    /*
     * A method that checks if there are any ladders in the queue
     * 
     * Return Value: Return true if empty, false otherwise
     */
    public boolean isEmpty() { return (size == 0); }
    
    /*
     * A method that returns how many ladders are in the queue
     * 
     * Return Value: int number of ladders
     */
    public int size() { return size; }
    
    /*
     * A method that clears all the ladders from the queue
     */
    public void clear() {
        size = 0; cap = 10;
        pQ = new LContainer[cap];
    }
    
    /*
     * A private method that switches two ladders in the queue
     */
    private void swapContainer(int firstPos, int secondPos) {
        LContainer temp;
        temp = pQ[firstPos];
        pQ[firstPos] = pQ[secondPos];
        pQ[secondPos] = temp;
    }
    
    /*
     * A private method that doubles the capacity of the array backing the queue
     */
    private void doubleCap() {
        cap *= 2;
        LContainer[] newArr = new LContainer[cap];
        for (int i = 1; i <= size; i++) {
            newArr[i] = pQ[i];
        }
        pQ = newArr;
    }
    
    // Gets parent index from a given position in queue
    private int pIndex(int position) { return position / 2; }
    // Gets left child of parent position in queue
    private int left(int position) { return position * 2; }
    // Gets right child of parent position in queue
    private int right(int position) { return (position * 2) + 1; }
    
    public String toString() {
    	String retval = "";
    	for (int i = 1; i <= size; i++) {
    		if (i == size) {
    			retval += pQ[i].ladder.toString() + " (" + pQ[i].priority + ")";
    		} else {
    			retval += pQ[i].ladder.toString() + " (" + pQ[i].priority + ")" + ", ";
    		}
    	}
    	return "{" + retval + "}";
    }
    
    /*
     * This is a private class Container that holds a List<String> that has
     * link names in them. It is assigned a priority which is based off
     * how many links the current link shares with the end link
     */
	private static class LContainer {
		List<String> ladder = new ArrayList<>();
		int priority = 0;
		
		public LContainer(List<String> ladder, int priority) {
			this.ladder = ladder;
			this.priority = priority;
		}
	}
}
