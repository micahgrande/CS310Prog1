/*  Micah Joseph Grande
    cssc0900
*/

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayLinearList<E> implements LinearListADT<E> {
	private int currentSize, maxSize,front,rear, removeIndex;
	private E[] storage;
	private E storageIndex;
	
	public ArrayLinearList(int size){
		maxSize = size;
		front = rear = currentSize = 0;
		storage = (E[]) new Object[maxSize];
	}
	
	public ArrayLinearList() {
		this (DEFAULT_MAX_CAPACITY);
	}

	//Adds the Object obj to the beginning of list and returns true if the list is not full.
	//returns false and aborts the insertion if the list is full.
	public boolean addFirst(E obj) {
		if (!isFull()) {
			front--;
			if (front < 0)
				front = maxSize-1;
			if (size() == 0)
				rear = front;
			storage[front] = obj;
			currentSize++;
			return true;
		}
		return false;
	}

	//Adds the Object obj to the end of list and returns true if the list is not full.
	//returns false and aborts the insertion if the list is full..  
	public boolean addLast(E obj) {
		if (!isFull()) {
			rear++;
			if (rear > maxSize-1)
				rear = 0;
			if (size() == 0)
				front = rear;
			storage[rear] = obj;
			currentSize++;
			return true; 
		}
		return false;
	}

	//Removes and returns the parameter object obj in first position in list if the list is not empty,  
	//null if the list is empty. 
	public E removeFirst() {
		if (!isEmpty()) {
			storageIndex = storage[front];
			currentSize--;
			front++;
			frontCheck();
			return storageIndex;
		}
		return null;
	}

	//Removes and returns the parameter object obj in last position in list if the list is not empty, 
	//null if the list is empty. 
	public E removeLast() {
		if(!isEmpty()) {
			storageIndex = storage[front];
			currentSize--;
			rear--;
			rearCheck();
			return storageIndex;
		}
		return null;
	}
	
	//Checks if difference between front and returnIndex is negative, and if it is, turn it positive.
	//The list is not modified.
	private int isNegative (int tmp) {
		if (tmp > 0)
			return (tmp);
		else
			return (-tmp);
	}
	
	//Checks if front needs to wrap around the right side of the array
	//The list is not modified.
	private void frontCheck () {
		if (front > maxSize-1)
			front = 0;
	}
	
	//Checks of rear needs to wrap around the left side of the array.
	//The list is not modified.
	private void rearCheck() {
		if (rear < 0)
			rear = maxSize-1;
	}

	//Removes and returns the parameter object obj from the list if the list contains it, null otherwise.
	//The ordering of the list is preserved.  The list may contain duplicate elements.  This method
	//removes and returns the first matching element found when traversing the list from first position.
	//Note that you may have to shift elements to fill in the slot where the deleted element was located.
	public E remove(E obj) {
		if(!isEmpty() && contains(obj)) {
			if(obj == storage[front]) {
				removeFirst();
				removeIndex--;
				return obj;
			}
			if(obj == storage[rear]) {
				removeLast();
				removeIndex--;
				return obj;
			}
			removeIndex = removeHelper(obj);
			front++;
			frontCheck();
			int distanceFront = front - removeIndex;
			distanceFront = isNegative(distanceFront);
			for (int i = 0; i < distanceFront+1; i++) {
				if (removeIndex == 0){
					storage[removeIndex] = storage[maxSize-1];
					removeIndex = maxSize-1;
				}
				else {
					storage[removeIndex] = storage[removeIndex-1];
					removeIndex--;
				}
			}
			currentSize--;
			return obj;
		}
					
		return null;
	}
	
	//Returns index in array storage of the first element that matches the obj trying to be found.
	//The list is not modified.
	private int removeHelper(E obj) {
		removeIndex = front;
		for (int i = 0; i < size(); i++ ) {
			if (removeIndex > maxSize-1)
				removeIndex = 0;
			if(((Comparable<E>) obj).compareTo(storage[removeIndex])== 0) 
				break;
			else removeIndex++;
		}
		return removeIndex;
	}

	//Returns the first element in the list, null if the list is empty.
	//The list is not modified.
	public E peekFirst() {
		if(!isEmpty()) 
			return storage[front];
		return null;
	}
	
	//Returns the last element in the list, null if the list is empty.
	//The list is not modified.
	public E peekLast() {
		if(!isEmpty())
			return storage[rear];
		return null;
	}

	//Returns true if the parameter object obj is in the list, false otherwise.
	//The list is not modified.
	public boolean contains(E obj) {
		return find(obj) != null;
	}
	
	//Returns the element matching obj if it is in the list, null otherwise.
	//In the case of duplicates, this method returns the element closest to front.
	//The list is not modified.
	public E find(E obj) {
		for (E tmp:this)
			if(((Comparable<E>)obj).compareTo(tmp)== 0)
				return tmp;
			return null;
	}

	//The list is returned to an empty state.
	public void clear() {
		currentSize = 0;
	}

	//Returns true if the list is empty, otherwise false
	public boolean isEmpty() {
		return (currentSize == 0);
	}

	//Returns true if the list is full, otherwise false
	public boolean isFull() {
		return (currentSize == maxSize);
	}

	//Returns the number of Objects currently in the list.
	public int size() {
		return currentSize;
	}

	//Returns an Iterator of the values in the list, presented in
	//the same order as the underlying order of the list. (front first, rear last)
	public Iterator<E> iterator() {
		return new IteratorHelper();
	}
	
	class IteratorHelper implements Iterator<E>{
		private int count, index;
		
		//Constructor for IteratorHelper
		public IteratorHelper() {
			index = front;
			count = 0;	
		}
		
		//Checks if the list has a next element
		public boolean hasNext() {
			return count != currentSize;
		}
		
		//Returns the next element in the array
		public E next() {
			if(!hasNext()) 
				throw new NoSuchElementException();
			E tmp = storage[index++];
			if (index == maxSize) 
				index = 0;
			count++;
			return tmp;
		}
		
		//Unused method
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
     
}