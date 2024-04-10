package org.example.common.atoms;


public class Pizza {
    private String serialNumber;
    private boolean cooked = false;

    public Pizza(String name) {
        this.serialNumber = name;
    }

    public boolean isCooked() {
        return cooked;
    }

    public void setCooked(boolean cooked) {
        this.cooked = cooked;
    }

    @Override
    public String toString() {
        return serialNumber;
    }
}