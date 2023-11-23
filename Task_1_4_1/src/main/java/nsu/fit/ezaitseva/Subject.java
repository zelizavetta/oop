package nsu.fit.ezaitseva;

public class Subject {
    private String name;
    private Mark mark;

    Subject(String name, Mark mark) {
        this.name = name;
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public Mark getMark() {
        return mark;
    }


}