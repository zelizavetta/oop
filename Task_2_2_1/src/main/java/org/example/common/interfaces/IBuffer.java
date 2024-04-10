package org.example.common.interfaces;

/**
 * With synchronization
 *
 * @param <T> the objects that contains in buffer
 */
public interface IBuffer<T> {
    /**
     * Notify all objects that are waiting to put something int buffer.
     */
    void notifyAllCanPut();

    /**
     * Notify object that's waiting to put something into buffer.
     */
    void notifyCanPut();

    /**
     * Notify all objects that are waiting to take something from buffer.
     */
    void notifyAllCanTake();

    /**
     * Notify object that's waiting to take something from the buffer.
     */
    void notifyCanTake();

    /**
     * Put something to the buffer. This method uses synchronized, so it's parallel save. Waits if buffer is full.
     *
     * @param object the object that will be stored into the buffer.
     */
    void put(T object) throws InterruptedException;

    /**
     * Take some object from the buffer.
     * This method uses synchronized, so it's parallel save. Waits if buffer is empty.
     *
     * @return took object from buffer.
     */

    T take() throws InterruptedException;


    /**
     * Check buffer for full.
     *
     * @return true if buffer is full
     */
    boolean isFull();


    /**
     * Check buffer for empty.
     *
     * @return true if buffer is empty
     */
    boolean isEmpty();

    /**
     * Return the current size.
     *
     * @return the currents size (amount of elements) in the buffer
     */
    int size();

    /**
     * Return max size of the buffer.
     *
     * @return max size of the buffer.
     */
    int capacity();


}