package org.example.roles.baker;

import java.util.Objects;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.example.common.atoms.Pizza;

/**
 * class describing baker part.
 *
 * @param id
 *
 * @param name
 *
 * @param productionTime
 *
 * @param errorTime
 */
@Slf4j
public record Baker(
        Long id,
        String name,
        int productionTime,
        int errorTime) {
    /**
     * Simulate cooking with sleeping.
     *
     * @param pizza the pizza that needs to cook
     *
     * @throws InterruptedException sometimes
     */
    public void cook(Pizza pizza) throws InterruptedException {
        final Random random = new Random();
        synchronized (this) {
            Thread.sleep(productionTime * 1000L + random.nextInt(errorTime + 1) * 1000L + 1);
        }
        pizza.setCooked(true);
    }

    @Override
    public String toString() {
        return "Baker{"
                + id
                + "-'"
                + name
                + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Baker that = (Baker) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}