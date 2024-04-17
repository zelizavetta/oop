package org.example.roles.client;

import java.util.function.Consumer;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.example.common.atoms.Order;
import org.example.common.atoms.Pizza;
import org.example.common.buffer.OrderBoard;


@Slf4j
/**
 * class for client.
 */
public class Client implements Runnable {

    private final OrderBoard orderBoard;
    private final String name = "Client " + new Random().nextInt(255);
    private final Random random = new Random();
    private final int waitingTime;
    private final int biasTime;
    private Pizza pizza;
    private volatile boolean isWorking;

    /**
     * constructor for client.
     *
     * @param orderBoard type OrderBoard
     *
     * @param waitingTime type int
     *
     * @param biasTime type int
     */
    public Client(OrderBoard orderBoard, int waitingTime, int biasTime) {
        this.orderBoard = orderBoard;
        this.waitingTime = waitingTime;
        this.biasTime = biasTime;
    }

    /**
     * func describing consuming pizza.
     *
     * @throws InterruptedException
     */
    public void consume() throws InterruptedException {
        synchronized (this) {
            if (Thread.interrupted()) {
                return;
            }
            this.wait();
        }
        log.info("{} got pizza {}", name, pizza);
    }

    /**
     * producing order.
     *
     * @throws InterruptedException if interrupted
     */
    public void produce() throws InterruptedException {
        pizza = null;
        String pizzaName = getRandomPizza();
        Consumer<Pizza> callable = (pizza) -> {
            this.pizza = pizza;
            log.warn("call {}", this.name);
            synchronized (this) {
                this.notify();
            }
        };
        Order order = new Order(pizzaName, callable, name);
        Thread.sleep((long) waitingTime * 1000 + (long) random.nextInt(biasTime) * 1000);
        orderBoard.put(order);
        log.info("{} put order {} to the orderBoard", name, order);
    }


    @Override
    public void run() {
        log.info("Start working");
        while (isWorking) {

            try {
                produce();
            } catch (InterruptedException e) {
                if (!isWorking) {
                    break;
                }
            }

            try {
                consume();
            } catch (InterruptedException e) {
                if (!isWorking) {
                    break;
                }
            }
        }
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    private String getRandomPizza() {
        int mod = 3;
        int x = random.nextInt(mod);

        return switch (x) {
            case 0 -> "Pepperoni";
            case 1 -> "Margaritta";
            default -> "Barbecue";
        };
    }

    public boolean havePizza() {
        return pizza != null;
    }

    @Override
    public String toString() {
        return "Client{"
                + "name='"
                + name
                + '\''
                + ", isWorking="
                + isWorking
                + '}';
    }
}