package nsu.fit.ezaitseva;

import java.util.ArrayList;

/**
 * class describing semester with order number of it and subjects
 */
public class Semester {
    private int number;
    private ArrayList<Subject> subjects;

    Semester(int number) {
        this.number = number;
        subjects = new ArrayList<>();
    }

    /**
     * method to get subject
     * @return all subjects
     */
    public ArrayList<Subject> getSubjects() {
        return subjects;
    }


    /**
     * adding subject
     * @param subject
     */
    public void addSubject(Subject subject) {
        this.subjects.add(subject);
    }

    /**
     * calculating possibility of scholarship of this semester
     * @return true if scholarship is possible, false if not
     */
    public boolean isIncScholarship() {
        return this.subjects.stream().allMatch(x -> x.getMark().getValue() == Mark.FIVE.getValue());
    }

    /**
     * calculating is there a bad mark or not
     * @return true if no bad marks in this semester, false if not
     */
    public boolean noBadMarks(){
        return this.subjects.stream().allMatch(x -> x.getMark().getValue() > Mark.THREE.getValue());
    }

}