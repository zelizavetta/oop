package org.example.common.buffer;

import org.example.common.interfaces.IBuffer;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class Buffer<T> implements IBuffer<T> {

    protected final Deque<T> buffer = new LinkedList<>();
    protected final int capacity;
    protected final Object canPut = new Object();
    protected final Object canTake = new Object();

    public Buffer(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Notify all objects that are waiting to put something int buffer.
     */
    @Override
    public void notifyAllCanPut() {
        synchronized (canPut) {
            canPut.notifyAll();
        }
    }

    /**
     * Notify object that's waiting to put something into buffer.
     */
    @Override
    public void notifyCanPut() {
        synchronized (canPut) {
            canPut.notify();
        }
    }

    /**
     * Notify all objects that are waiting to take something from buffer.
     */
    @Override
    public void notifyAllCanTake() {
        synchronized (canTake) {
            canTake.notifyAll();
        }
    }

    /**
     * Notify object that's waiting to take something from the buffer.
     */
    @Override
    public void notifyCanTake() {
        synchronized (canTake) {
            canTake.notify();
        }
    }

    /**
     * Put something to the buffer. This method uses synchronized, so it's parallel save. Waits if buffer is full.
     *
     * @param object the object that will be stored into the buffer.
     */
    @Override
    public void put(T object) throws InterruptedException {
        synchronized (canPut) {
            while (isFull()) {
                canPut.wait();
            }
            synchronized (buffer) {
                buffer.addFirst(object);
            }
        }
//        notifyAllCanTake();
        notifyCanTake();
    }

    /**
     * Take some object from the buffer.
     * This method uses synchronized, so it's parallel save. Waits if buffer is empty.
     *
     * @return took object from buffer.
     */
    @Override
    public T take() throws InterruptedException {
        T tookObject;
        synchronized (canTake) {
            while (isEmpty()) {
                canTake.wait();
            }
            synchronized (buffer) {
                tookObject = buffer.removeLast();
            }
        }
//        notifyAllCanPut();
        notifyCanPut();
        return tookObject;
    }



    /**
     * Check buffer for full.
     *
     * @return true if buffer is full
     */
    @Override
    public boolean isFull() {
        synchronized (buffer) {
            return buffer.size() == capacity;
        }
    }

    /**
     * Check buffer for empty.
     *
     * @return true if buffer is empty
     */
    @Override
    public boolean isEmpty() {
        synchronized (buffer) {
            return buffer.isEmpty();
        }
    }

    /**
     * Return the current size.
     *
     * @return the currents size (amount of elements) in the buffer
     */
    @Override
    public int size() {
        synchronized (buffer) {
            return buffer.size();
        }
    }

    /**
     * Return max size of the buffer.
     *
     * @return max size of the buffer.
     */
    @Override
    public int capacity() {
        return capacity;
    }
}