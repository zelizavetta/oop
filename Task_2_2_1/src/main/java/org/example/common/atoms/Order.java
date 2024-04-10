package org.example.common.atoms;

import java.util.function.Consumer;


public record Order(String pizzaSerialNumber, Consumer<Pizza> orderCallback, String clientNumber) {

    @Override
    public String toString() {
        return "Order{" +
                "pizzaSerialNumber='" + pizzaSerialNumber + '\'' +
                ", clientNumber='" + clientNumber + '\'' +
                '}';
    }
}