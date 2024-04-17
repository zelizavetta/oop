package org.example.common.interfaces;

/**
 * interface describing pizzeria starting and stopping
 */
public interface PizzaService {
    /**
     * Start working. maybe download objects.
     */
    void startWorking();

    /**
     * Stop working. It may be not instantly.
     */


    void stopWorking();
}