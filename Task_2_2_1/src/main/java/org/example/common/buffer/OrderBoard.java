package org.example.common.buffer;

import org.example.common.configuration.Configuration;
import org.example.common.atoms.Order;

public class OrderBoard extends Buffer<Order> {
    public OrderBoard(int capacity) {
        super(capacity);
    }

    public OrderBoard() {
        super(Configuration.MAX_ORDER_BOARD_SIZE);
    }
}