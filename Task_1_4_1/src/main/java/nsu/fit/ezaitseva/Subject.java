package nsu.fit.ezaitseva;

/**
 * class describing subject of the record book
 */
public class Subject {
    private String name;
    private Mark mark;

    Subject(String name, Mark mark) {
        this.name = name;
        this.mark = mark;
    }

    /**
     * method to get name of the subject
     * @return name of the subject
     */
    public String getName() {
        return name;
    }

    /**
     * method to get subject mark
     * @return subject mark
     */
    public Mark getMark() {
        return mark;
    }


}