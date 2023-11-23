package nsu.fit.ezaitseva;

public class Main {
    public static void main(String[] args) {
        Subject subj1_2 = new Subject("Russian", Mark.FOUR);
        Subject subj1_1 = new Subject("Russian", Mark.FIVE);
        Subject subj2_1 = new Subject("English", Mark.FIVE);
        Subject subj3_2 = new Subject("Italian", Mark.FIVE);
        Subject subj4_2 = new Subject("French", Mark.FIVE);
        Subject subj5_1 = new Subject("German", Mark.FIVE);

        Semester sem1 = new Semester(1);
        Semester sem2 = new Semester(2);

        sem1.addSubject(subj1_1);
        sem1.addSubject(subj2_1);
        sem1.addSubject(subj5_1);
        sem2.addSubject(subj3_2);
        sem2.addSubject(subj4_2);
        sem2.addSubject(subj1_2);

        RecordBook recordBook = new RecordBook();
        recordBook.setFinalTask(Mark.FIVE);
        recordBook.addSemester(sem1);
        recordBook.addSemester(sem2);

        System.out.println(recordBook.isRedDiploma());
        System.out.println(recordBook.getAverageMark());
        System.out.println(sem1.isIncScholarship());
        System.out.println(sem2.isIncScholarship());


    }
}