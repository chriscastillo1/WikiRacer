import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/*
 * Author: Chris Castillo
 * Purpose: This program constructs a priority queue backed by an array. This
 * 			array follows the Binary Max-Heap convention where the biggest priority is
 * 			placed at the front of the queue (NOTE: Front is Index 1, not 0)
 * Class: CSC 210
 * 
 * This program contains several public methods for accessing the Priority Queue:
 * enqueue - Puts a container into the queue based off priority
 * dequeue - Removes a container from the front of the queue
 * isEmpty - Checks if the queue has no containers in it
 * size    - Returns the number of containers in the queue
 * clear   - Removes all containers from the queue
 * 
 * EXAMPLE USAGE:
 * MaxPQ testPQ = new MaxPQ();
 * testPQ.enqueue(Container ladder)
 * testPQ.dequeue();
 * 
 * All commands above are supported by this program. It assumes all inputs for those
 * methods are of similar format as described above
 */
public class MaxPQ {
	
	private int cap = 10;
	private int size = 0;
	private Container[] pQ;
	
	public MaxPQ() {
		pQ = new Container[cap];
	}
	
	/*
	 * A method that adds a new ladder to the priority queue based on the
	 * Binary Max-Heap order (Where biggest value is up front)
	 */
	public void enqueue(Container ladder) {
		if (size + 1 >= cap) { doubleCap(); }
		if (size == 0) { size++; pQ[size] = ladder; return; }
		
		size++;
		pQ[size] = ladder;
		bubbleUp(size, pIndex(size));
	}
	
	/*
	 * NOTE: Overloaded Enqueue method
	 * This method takes in a ladder and an int priority and adds it to the queue
	 * (NOTE: STILL KEEPS HEAP ORDER, MAX-HEAP)
	 */
	public void enqeueue(List<String> ladder, int priority) {
		Container newContainer = new Container(ladder, priority);
		this.enqueue(newContainer);
	}
	
	/*
	 * A private helper method that moves the current patient position up the
	 * priority queue if the priority is bigger than its parent position.
	 * (ie If parent has priority of 5 and current has priority of 8, it swaps)
	 */
	private void bubbleUp(int pos, int parent) {
		if (pos == 1) { return; }
		// If the parent is LESS than current, it swaps places
		if (pQ[pos].priority > pQ[parent].priority) {
			swapContainer(pos, parent); pos = parent;
			bubbleUp(pos, pIndex(pos));
		}
	}
	
	/*
	 * A private method that doubles the capacity of the array that backs the
	 * priority queue
	 */
	private void doubleCap() {
		cap *= 2;
        Container[] newArr = new Container[cap];
        for (int i = 1; i <= size; i++) {
            newArr[i] = pQ[i];
        }
        pQ = newArr;
	}
	
	/*
	 * A method that returns the ladder (list of links) from the queue
	 * (NOTE: It will then bubble down the next element to maintain proper order)
	 */
	public List<String> dequeue() {
		if (size == 0) { throw new NoSuchElementException(); }
		
		List<String> ladderList = pQ[1].ladder;
		pQ[1] = pQ[size]; size--;
		bubbleDown(1);
		return ladderList;
	}
	
	/*
	 * A private method that bubbles a container down from the front of queue until it
	 * reaches the correct priority position (Children priority CANT be bigger than parent)
	 */
	private void bubbleDown(int pos) {
		if (pos*2 > size) { return; }
		
		int switchPos = compareLeftRight(pos);
		if (pQ[pos].priority < pQ[switchPos].priority) {
			swapContainer(pos, switchPos); pos = switchPos;
			bubbleDown(pos);
		} else return;
	}
	
	/*
	 * A private method that compares two children containers' priorities.
	 * It returns the index of the child that has a bigger priority
	 */
	private int compareLeftRight(int pos) {
		int left = left(pos); int right = right(pos);
		if (left <= size && right > size) { return left; }
		
		if (pQ[left].priority == pQ[right].priority) { return left; }
		
		if (pQ[left].priority > pQ[right].priority) {
			return left;
		} else return right;
	}
	
	/*
	 * A private method that swaps two containers places with each other
	 */
	private void swapContainer(int firstPos, int secondPos) {
		Container temp;
        temp = pQ[firstPos];
        pQ[firstPos] = pQ[secondPos];
        pQ[secondPos] = temp;
	}
	
	// A method that checks if the queue is empty
	public boolean isEmpty() { return (size == 0); }
	
	// A method that returns the number of containers in the queue
	public int size() { return size; }
	
	// A method that removes all containers from the queue
	public void clear() { size = 0; cap = 10; pQ = new Container[cap]; }
	
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
	 * A private class Container which holds a List<String> called ladder and an
	 * int priority. This is built for the Binary-Max heap implementation
	 */
	private static class Container {
		List<String> ladder = new ArrayList<>();
		int priority;
		// A default constructor which initializes ladder and priority
		public Container(List<String> ladder, int priority) {
			this.ladder = ladder;
			this.priority = priority;
		}
	}
}
