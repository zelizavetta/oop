package org.example.roles.client;

import lombok.extern.slf4j.Slf4j;
import org.example.common.buffer.OrderBoard;
import org.example.common.configuration.Configuration;
import org.example.common.configuration.FactoryConfiguration;
import org.example.common.interfaces.PizzaService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;


@Slf4j
public class ClientService implements PizzaService {
    private final Map<Client, Thread> clientThreadMap = new LinkedHashMap<>();
    private final OrderBoard orderBoard;

    private FactoryConfiguration factoryConfiguration;

    public ClientService(FactoryConfiguration factoryConfiguration) {
        orderBoard = factoryConfiguration.getOrderBoard();
    }

    /**
     * Start working. maybe download objects.
     */
    @Override
    public void startWorking() {
        initialize();
        log.info("Client service start working, amount of bakers: {}", clientThreadMap.size());
        clientThreadMap.forEach(((client, thread) -> {
            client.setWorking(true);
            thread.start();
            thread.setName(client.toString());
        }));

    }

    /**
     * Stop working. It may be not instantly.
     */
    @Override
    public void stopWorking() {
        log.info("Client service stop working, amount of clients: {}", clientThreadMap.size());
        clientThreadMap.forEach((client, thread) -> {
            client.setWorking(false);
            thread.interrupt();
        });
    }

    private void initialize() {
        int clientsAmount = Configuration.CLIENTS_AMOUNT;
        Random random = new Random();
        for (int i = 0; i < clientsAmount; i++) {
            Client client = new Client(orderBoard, 5 + random.nextInt(15),
                    random.nextInt(5) + 1);
            clientThreadMap.put(client, new Thread(client, client.toString()));
        }
    }

}