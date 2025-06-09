package org.example;

import java.util.ArrayList;

public class Queue<T> {
    private ArrayList<T> queue;
    private int capacity;

    public Queue(int capacity) {
        queue = new ArrayList<T>();
        this.capacity = capacity;
    }

    public boolean enqueue(T element) {
        if (!this.isFull()) {
            queue.add(element);
            return true;
        }
        return false;
    }

    public T dequeue() {
        if(queue.isEmpty()){
            return null;
        }
        return queue.remove(0);
    }

    public boolean isFull() {
        if (queue.size() >= capacity) {
            return true;
        }
        return false;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    //public abstract boolean equals();

    //public abstract int compareTo();
}
