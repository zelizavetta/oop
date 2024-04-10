package org.example.common.interfaces;

import java.util.Collection;

public interface IStorage<T> extends IBuffer<T> {
    /**
     * Take at least one! It is waiting if storage is empty.
     * Removes all available elements from this queue and adds them
     * to the given collection.
     *
     * @param collection the collection to transfer elements into
     * @return the number of elements transferred
     */

    int drainTo(Collection<? super T> collection) throws InterruptedException;

    /**
     * Take at least one element. It is waiting if storage is empty!
     * Removes at most the given number of available elements
     * from this queue and adds them to the given collection.
     *
     * @param collectionForSaving – the collection to transfer elements into
     * @param maxElements         – the maximum number of elements to transfer
     * @return the number of elements transferred
     */
    int drainTo(Collection<? super T> collectionForSaving, int maxElements) throws InterruptedException;
}