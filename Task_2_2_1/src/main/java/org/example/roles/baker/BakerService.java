package org.example.roles.baker;

import lombok.extern.slf4j.Slf4j;
import org.example.common.atoms.Delivery;
import org.example.common.buffer.OrderBoard;
import org.example.common.buffer.Storage;
import org.example.common.configuration.FactoryConfiguration;
import org.example.common.atoms.Order;
import org.example.common.interfaces.PizzaService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class BakerService implements PizzaService {
    private final Map<BakerManager, Thread> bakerThreadMap = new LinkedHashMap<>();
    private final Storage<Delivery> storage;
    private final OrderBoard orderBoard;
    private final BakerRepository bakerRepository;

    public BakerService(FactoryConfiguration sharedClassFactory) {
        storage = sharedClassFactory.getStorage();
        orderBoard = sharedClassFactory.getOrderBoard();
        bakerRepository = sharedClassFactory.getBakerRepository();
    }

    /**
     * Start working. maybe download objects.
     */
    @Override
    public void startWorking() {
        initialize();
        log.info("Baker service start working, amount of bakers: {}", bakerThreadMap.size());
        bakerThreadMap.forEach(((bakerManager, thread) -> {
            bakerManager.setWorking(true);
            thread.start();
            thread.setName(bakerManager.toString());
        }));

    }

    /**
     * Stop working. It may be not instantly.
     */
    @Override
    public void stopWorking() {
        log.info("Baker service stop working, amount of bakers: {}", bakerThreadMap.size());
        bakerThreadMap.forEach((bakerManager, thread) -> {
            bakerManager.setWorking(false);
            thread.interrupt();
        });
    }

    private void initialize() {
        List<Baker> bakerEntities = bakerRepository.findAll();
        for (Baker baker : bakerEntities) {
            BakerManager bakerManager = new BakerManager(baker, storage, orderBoard);
            bakerThreadMap.put(bakerManager, new Thread(bakerManager, bakerManager.toString()));
        }
    }


}