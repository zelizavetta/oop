package nsu.fit.ezaitseva;

/**
 * Set of the marks
 */
public enum Mark {

    FIVE(5),
    FOUR(4),
    THREE(3),
    TWO(2);
    private int value;

    Mark(int value) {
        this.value = value;
    }

    /**
     * to get value of the mark
     * @return value of the mark
     */
    public  int getValue() {
        return value;
    }

}
