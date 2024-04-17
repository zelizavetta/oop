package org.example.common.atoms;

/**
 * class describing pizza.
 */
public class Pizza {
    private String serialNumber;
    private boolean cooked = false;

    /**
     * constructor for Pizza.
     *
     * @param name type String
     */
    public Pizza(String name) {
        this.serialNumber = name;
    }

    /**
     * setting cooked.
     *
     * @param cooked type boolean
     */
    public void setCooked(boolean cooked) {
        this.cooked = cooked;
    }

    @Override
    public String toString() {
        return serialNumber;
    }
}