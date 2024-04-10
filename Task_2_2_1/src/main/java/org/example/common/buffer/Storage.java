package org.example.common.buffer;

import org.example.common.configuration.Configuration;
import org.example.common.interfaces.IStorage;

import java.util.Collection;
import java.util.Iterator;

public class Storage<T> extends Buffer<T> implements IStorage<T> {
    public Storage(int capacity) {
        super(capacity);
    }

    public Storage() {
        super(Configuration.MAX_STORAGE_SIZE);
    }

    /**
     * Removes all available elements from this queue and adds them
     * to the given collection.
     *
     * @param collection the collection to transfer elements into
     * @return the number of elements transferred
     */
    @Override
    public int drainTo(Collection<? super T> collection) throws InterruptedException {
        int amount;
        synchronized (canTake) {
            while (isEmpty()) {
                canTake.wait();
            }
            synchronized (buffer) {
                collection.addAll(buffer);
                amount = buffer.size();
                buffer.clear();
            }
        }
        return amount;
    }

    /**
     * Removes at most the given number of available elements from this queue and adds them to the given collection.
     *
     * @param collectionForSaving – the collection to transfer elements into
     * @param maxElements         – the maximum number of elements to transfer
     * @return the number of elements transferred
     */
    @Override
    public int drainTo(Collection<? super T> collectionForSaving, int maxElements) throws InterruptedException {
        int amount = 0;
        if (maxElements < 0) {
            throw new IllegalArgumentException("Negative maxElemenets");
        }
        if (maxElements == 0) {
            return 0;
        }
        synchronized (canTake) {
            while (isEmpty()) {
                canTake.wait();
            }
            synchronized (buffer) {
                Iterator<T> iterator = buffer.iterator();
                int i = maxElements;
                while (iterator.hasNext() && i > 0) {
                    collectionForSaving.add(iterator.next());
                    iterator.remove();
                    i--;
                }
            }
        }
        return amount;
    }
}