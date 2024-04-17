package org.example.common.atoms;

import java.util.function.Consumer;

/**
 * class describing delivery with pizza, client name
 */
public class Delivery {
    private final Pizza pizza;
    private final Consumer<Pizza> orderCallback;
    private final String clientName;

    /**
     * constructor for Delivery
     *
     * @param pizza type Pizza
     *
     * @param orderCallback type Consumer<Pizza>
     *
     * @param clientName type String
     */
    public Delivery(Pizza pizza, Consumer<Pizza> orderCallback, String clientName) {
        this.pizza = pizza;
        this.orderCallback = orderCallback;
        this.clientName = clientName;
    }

    public void callOrderOwner() {
        orderCallback.accept(pizza);
    }

    @Override
    public String toString() {
        return "DeliveryOrder{"
                + "pizza="
                + pizza
                + ", clientName='"
                + clientName
                + '\''
                + '}';
    }
}