package org.example.roles.courier;

import java.util.Collection;
import java.util.List;

/**
 * interface for courier
 */
public interface CourierRepository {
    Courier save(Courier courier);

    void delete(Courier courier);

    void delete(Long courierId);

    void deleteAll();

    List<Courier> findAll();

    void saveAll(Collection<Courier> couriers);
}