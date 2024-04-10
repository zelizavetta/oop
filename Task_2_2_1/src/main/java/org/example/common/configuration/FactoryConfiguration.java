package org.example.common.configuration;

import org.example.common.buffer.OrderBoard;
import org.example.common.buffer.Storage;
import org.example.common.atoms.Delivery;
import org.example.roles.baker.BakerRepository;
import org.example.roles.baker.JSONBaker;
import org.example.roles.courier.CourierRepository;
import org.example.roles.courier.JSONCourier;

public class FactoryConfiguration {
    private final OrderBoard orderBoard = new OrderBoard(Configuration.MAX_ORDER_BOARD_SIZE);
    private final Storage<Delivery> storage = new Storage<>(Configuration.MAX_STORAGE_SIZE);

    private final BakerRepository bakerRepository = new JSONBaker();

    private final CourierRepository courierRepository = new JSONCourier();

    /**
     * Idempotent function, that return the same object - bakerRepository.
     *
     * @return the same bakerRepository.
     */
    public BakerRepository getBakerRepository() {
        return bakerRepository;
    }

    /**
     * Idempotent function, that return the same object - courierRepository.
     *
     * @return the same courierRepository.
     */
    public CourierRepository getCourierRepository() {
        return courierRepository;
    }

    /**
     * Idempotent function, that return the same object - orderBoard.
     *
     * @return the same orderBoard.
     */
    public OrderBoard getOrderBoard() {
        return orderBoard;
    }

    /**
     * Idempotent function, that return the same object - storage.
     *
     * @return the same storage.
     */
    public Storage<Delivery> getStorage() {
        return storage;
    }
}