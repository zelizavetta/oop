package nsu.fit.ezaitseva;

import java.util.*;
import java.util.stream.Stream;

public class RecordBook {
    private Mark finalTask;
    private ArrayList<Semester> allSemesters;

    RecordBook() {
        this.allSemesters = new ArrayList<>();
    }

    public void addSemester(Semester semester) {
        allSemesters.add(semester);
    }

    public Semester getSemesterByNumber(int number) {
        return allSemesters.get(number - 1);
    }

    public void setFinalTask(Mark mark) {
        this.finalTask = mark;
    }

    public double getAverageMark() {
        Stream<Subject> subjects = Stream.empty();
        for (var semester : allSemesters) {
            subjects = Stream.concat(subjects, semester.getSubjects().stream());
        }
        double avMark = subjects
                .mapToInt(x -> x.getMark().getValue())
                .average().getAsDouble();
        return Math.ceil(avMark * 10) / 10;
    }

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
        return (noBadMarks
                &
                (finalTask == Mark.FIVE)
                &
                ((double) fives / (fours + fives)) >= 0.75
        );
    }

}