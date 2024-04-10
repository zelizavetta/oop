package org.example.roles.baker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.common.configuration.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JSONBaker implements BakerRepository {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File fileBakers;

    private List<Baker> bakers;

    public JSONBaker() {
        fileBakers = new File(Configuration.BAKERS);
        if (!fileBakers.exists()) {
            bakers = initializationList();
            writeToFile(bakers);
        } else {
            try {
                bakers = objectMapper.readValue(fileBakers, new TypeReference<List<Baker>>() {
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

    /**
     * Write/save into the file object. Use JSON.
     * Usually saves list of bakers.
     *
     * @param object the object for saving
     */
    private void writeToFile(Object object) {
        try {
            objectMapper.writeValue(fileBakers, object);
        } catch (IOException e) {
            fileBakers.mkdirs();
            try {
                fileBakers.createNewFile();
                objectMapper.writeValue(fileBakers, object);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Creates initialize values, if there is no file
     *
     * @return list of couriers
     */
    private List<Baker> initializationList() {
        List<Baker> bakers = new ArrayList<>();
        bakers.add(new Baker(0L, "paul", 4, 4));
        bakers.add(new Baker(1L, "albert", 8, 1));
        return bakers;
    }

    @Override
    public Baker save(Baker baker) {
        bakers.add(baker);
        writeToFile(bakers);

        return baker;
    }

    @Override
    public void delete(Baker baker) {
        bakers.remove(baker);
        writeToFile(bakers);
    }

    @Override
    public void delete(Long bakerId) {
        bakers = bakers.stream().filter(baker -> !baker.id().equals(bakerId)).collect(Collectors.toList());
        writeToFile(bakers);
    }

    @Override
    public void deleteAll() {
        bakers.clear();
        writeToFile(bakers);
    }

    @Override
    public List<Baker> findAll() {
        return new ArrayList<>(bakers);
    }

    @Override
    public void saveAll(Collection<Baker> bakers) {
        this.bakers.addAll(bakers);
        writeToFile(bakers);
    }
}