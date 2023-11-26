package nsu.fit.ezaitseva;

import java.util.*;
import java.util.stream.Stream;


/**
 * class describing record book with field: mark and list of all semesters
 */
public class RecordBook {
    private Mark finalTask;
    private ArrayList<Semester> allSemesters;

    RecordBook() {
        this.allSemesters = new ArrayList<>();
    }


    /**
     * adding new semester
     * @param semester
     */
    public void addSemester(Semester semester) {
        allSemesters.add(semester);
    }


    /**
     * method to get semester
     * @param number - order number of the semester
     * @return semester by its order number
     */
    public Semester getSemesterByNumber(int number) {
        return allSemesters.get(number - 1);
    }

    /**
     * setting mark for final task
     * @param mark - mark for this task
     */
    public void setFinalTask(Mark mark) {
        this.finalTask = mark;
    }

    /**
     * calculating average mark of all subjects
     * @return average mark
     */
    public double getAverageMark() {
        Stream<Subject> subjects = Stream.empty();
        for (var semester : allSemesters) {
            subjects = Stream.concat(subjects, semester.getSubjects().stream());
        }

        OptionalDouble avMark = subjects
                .mapToInt(x -> x.getMark().getValue())
                .average();
        if (avMark.isEmpty()) {
            return 0.0;
        }
        return Math.ceil(avMark.getAsDouble() * 10) / 10;

    }

    /**
     * calculating is red diploma possible
     * @return true if re diploma is possible, false if not
     */
    public boolean isRedDiploma() {
        HashMap<String, Mark> lastMark = new HashMap<>();
        int semestersQuantity = allSemesters.size();
        for (int i = 0; i < semestersQuantity; i++) {
            allSemesters.get(i).getSubjects()
                    .stream()
                    .forEach(x -> lastMark
                            .put(x.getName(), x.getMark()));
        }
        int fives = 0;
        int fours = 0;
        for (HashMap.Entry<String, Mark> e : lastMark.entrySet()) {
            if (e.getValue() == Mark.FIVE) {
                fives += 1;
            } else if (e.getValue() == Mark.FOUR) {
                fours += 1;
            }
        }
        boolean noBadMarks = allSemesters.stream().allMatch(x -> x.noBadMarks());
        if (fives + fours == 0) {
            return false;
        }
        return (noBadMarks
                &
                (finalTask == Mark.FIVE)
                &
                ((double) fives / (fours + fives)) >= 0.75
        );
    }

}