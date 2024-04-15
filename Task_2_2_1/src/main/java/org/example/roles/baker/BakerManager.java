package org.example.roles.baker;

import lombok.extern.slf4j.Slf4j;
import org.example.common.buffer.OrderBoard;
import org.example.common.buffer.Storage;
import org.example.common.atoms.Delivery;
import org.example.common.atoms.Order;
import org.example.common.atoms.Pizza;


@Slf4j
public class BakerManager implements Runnable {

    private final Baker baker;

    private final Storage storage;

    private final OrderBoard orderBoard;

    private volatile boolean isWorking = true;

    private Order order = null;


    public BakerManager(Baker baker, Storage storage, OrderBoard orderBoard) {
        this.baker = baker;
        this.storage = storage;
        this.orderBoard = orderBoard;
    }

    /**
     * Get order from orderBoard
     */
    public void consume() throws InterruptedException {
        order = orderBoard.take();
    }

    /**
     * Cooks pizza (waiting) and puts into storage
     *
     * @throws InterruptedException sometimes
     */
    public void produce() throws InterruptedException {
        Pizza pizza = new Pizza(order.pizzaSerialNumber());
        baker.cook(pizza);
        log.info("{} Cooked pizza {} to {}", baker, order.pizzaSerialNumber(), order.clientNumber());
        Delivery deliveryOrder = new Delivery(pizza, order.orderCallback(), order.clientNumber());
        order = null;
        storage.put(deliveryOrder);
        log.info("{} Put order {} to storage", baker, deliveryOrder);
    }

    /**
     * Starts getting orders and cooking pizzas. After that, put storage and start cycle again.
     */

    @Override
    public void run() {
        log.info("{} starts working", baker);
        while (isWorking) {
            try {
                consume();
                log.info("{} took order {}", baker, order);
            } catch (InterruptedException e) {
                if (!isWorking) {
                    break;
                }
            }

            try {
                produce();

            } catch (InterruptedException e) {
                if (!isWorking) {
                    break;
                }
            }
        }

    }

    /**
     * Set working type for stop running
     * @param working true - working, false - stop working
     */
    public void setWorking(boolean working) {
        isWorking = working;
    }

    /**
     * Just for test
     * @return order
     */
    public Order getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "Baker{" +
                "baker=" + baker +
                ", isWorking=" + isWorking +
                '}';
    }
}