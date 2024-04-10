package org.example.pizzeria;

import lombok.extern.slf4j.Slf4j;
import org.example.common.configuration.FactoryConfiguration;
import org.example.roles.baker.BakerService;
import org.example.roles.client.ClientService;
import org.example.roles.courier.CourierService;


@Slf4j
public class Pizzeria {
    private final BakerService bakerService;
    private final CourierService courierService;
    private final ClientService clientService;

    private final FactoryConfiguration factory = new FactoryConfiguration();

    public Pizzeria() {
//        log.info("Pizzeria created");
        bakerService = new BakerService(factory);
        clientService = new ClientService(factory);
        courierService = new CourierService(factory);
    }

    /**
     * Start working. maybe download objects.
     */
    public void start() {
//        log.info("Pizzeria start new beautiful day");
        bakerService.startWorking();
        clientService.startWorking();
        courierService.startWorking();
    }

    /**
     * Stop working. It may be not instantly.
     */
    public void stop() {
        bakerService.stopWorking();
        clientService.stopWorking();
        courierService.stopWorking();
    }
}