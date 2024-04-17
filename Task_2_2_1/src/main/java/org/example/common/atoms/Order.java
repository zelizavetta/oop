package org.example.common.atoms;

import java.util.function.Consumer;

/**
 * class describing order of pizzeria
 * @param pizzaSerialNumber
 * @param orderCallback
 * @param clientNumber
 */
public record Order(String pizzaSerialNumber, Consumer<Pizza> orderCallback, String clientNumber) {

    @Override
    public String toString() {
        return "Order{"
                + "pizzaSerialNumber='"
                + pizzaSerialNumber
                + '\''
                + ", clientNumber='"
                + clientNumber
                + '\''
                + '}';
    }
}