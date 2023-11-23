package nsu.fit.ezaitseva;

import java.util.ArrayList;

public class Semester {
    private int number;
    private ArrayList<Subject> subjects;

    Semester(int number) {
        this.number = number;
        subjects = new ArrayList<>();
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public int getNumber() {
        return number;
    }

    public void addSubject( Subject subject) {
        this.subjects.add(subject);
    }

    public boolean isIncScholarship() {
        return this.subjects.stream().allMatch(x -> x.getMark().getValue() == Mark.FIVE.getValue());
    }

    public boolean noBadMarks(){
        return this.subjects.stream().allMatch(x -> x.getMark().getValue() > Mark.THREE.getValue());
    }

}