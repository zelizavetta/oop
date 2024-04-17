package org.example.common.buffer;

import org.example.common.atoms.Order;
import org.example.common.configuration.Configuration;

/**
 * class describing order board
 */
public class OrderBoard extends Buffer<Order> {
    public OrderBoard(int capacity) {
        super(capacity);
    }

    public OrderBoard() {
        super(Configuration.MAX_ORDER_BOARD_SIZE);
    }
}