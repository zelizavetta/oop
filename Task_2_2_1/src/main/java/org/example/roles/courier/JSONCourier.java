package org.example.roles.courier;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.common.configuration.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class JSONCourier implements CourierRepository {
    private final List<Courier> couriers;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File fileCouriers;

    public JSONCourier() {
        fileCouriers = new File(Configuration.COURIERS);
        if (!fileCouriers.exists()) {
            couriers = initializationList();
            writeToFile(couriers);
        } else {
            try {
                couriers = objectMapper.readValue(fileCouriers, new TypeReference<List<Courier>>() {
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Creates initialize values, if there is no file
     *
     * @return list of couriers
     */
    private List<Courier> initializationList() {
        List<Courier> courierEntities = new ArrayList<>();
        courierEntities.add(new Courier(0L, "paul", 4, 4, 2));
        courierEntities.add(new Courier(1L, "albert", 8, 1, 1));
        courierEntities.add(new Courier(2L, "ban", 5, 2, 3));
        return courierEntities;
    }

    /**
     * Write/save into the file object. Use JSON.
     * Usually saves list of couriers.
     *
     * @param object the object for saving
     */
    private void writeToFile(Object object) {
        try {
            objectMapper.writeValue(fileCouriers, object);
        } catch (IOException e) {
            fileCouriers.mkdirs();
            try {
                fileCouriers.createNewFile();
                objectMapper.writeValue(fileCouriers, object);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public Courier save(Courier courier) {
        couriers.add(courier);
        writeToFile(couriers);
        return courier;
    }

    @Override
    public void delete(Courier courier) {
        couriers.remove(courier);
        writeToFile(couriers);
    }

    @Override
    public void delete(Long courierId) {
        Iterator<Courier> iterator = couriers.iterator();
        while (iterator.hasNext()) {
            var cour = iterator.next();
            if (cour.getId().equals(courierId)) {
                iterator.remove();
                break;
            }
        }
        writeToFile(couriers);
    }

    @Override
    public void deleteAll() {
        couriers.clear();
        writeToFile(couriers);
    }

    @Override
    public List<Courier> findAll() {
        return new ArrayList<>(couriers);
    }

    @Override
    public void saveAll(Collection<Courier> couriers) {
        this.couriers.addAll(couriers);
        writeToFile(couriers);
    }

}