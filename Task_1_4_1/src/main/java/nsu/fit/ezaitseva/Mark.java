package nsu.fit.ezaitseva;

public enum Mark {

    FIVE(5),
    FOUR(4),
    THREE(3),
    TWO(2);
    private int value;

    Mark(int value) {
        this.value = value;
    }

    public  int getValue() {
        return value;
    }

}
