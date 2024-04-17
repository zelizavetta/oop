package org.example.roles.courier;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Iterator;
import java.util.Objects;
import lombok.Setter;
import org.example.common.atoms.Delivery;

@Getter
@Setter
@NoArgsConstructor
/**
 * class for courier
 */
public class Courier {

    private Long id;
    private String name;
    private int deliveryTime;
    private int errorTime;
    private int capacity;
    @JsonIgnore
    private final List<Delivery> orderList = new ArrayList<>();

    /**
     * constructor for courier
     *
     * @param id type Long
     *
     * @param name type String
     *
     * @param deliveryTime type int
     *
     * @param errorTime type int
     *
     * @param capacity type int
     */
    public Courier(Long id, String name, int deliveryTime, int errorTime, int capacity) {
        this.id = id;
        this.name = name;
        this.deliveryTime = deliveryTime;
        this.errorTime = errorTime;
        this.capacity = capacity;
    }

    /**
     * adding order for courier
     *
     * @param orders
     */
    public void addOrder(Collection<Delivery> orders) {
        int size = orderList.size();
        for (Delivery order : orders) {
            if (size >= capacity) {
                return;
            }
            orderList.add(order);
            size++;
        }
    }

    /**
     * deliver func
     *
     * @throws InterruptedException if interrupting
     */
    public void deliver() throws InterruptedException {
        Random random = new Random();
        Iterator<Delivery> iterator = orderList.iterator();
        while (iterator.hasNext()) {
            var order = iterator.next();
            iterator.remove();
            order.callOrderOwner();
        }
        orderList.clear();
        synchronized (this) {
            Thread.sleep((long) 1000 * deliveryTime + random.nextInt(1000 * errorTime));
        }
    }

    /**
     * define ability of courier to take order
     *
     * @return ability of courier to take order
     */
    public int canTake() {
        return capacity - orderList.size();
    }

    @Override
    public String toString() {
        return "Courier{"
                + id
                + "-'"
                + name
                + '\''
                + "orders:"
                + orderList
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Courier that = (Courier) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * getting id
     *
     * @return id of courier
     */
    public Long getId() {
        return this.id;
    }
}
