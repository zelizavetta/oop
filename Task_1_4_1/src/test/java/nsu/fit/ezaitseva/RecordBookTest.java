package nsu.fit.ezaitseva;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileNotFoundException;
import java.util.stream.Stream;


public class RecordBookTest {

    static RecordBook excellentRecordBook = new RecordBook();
    static RecordBook normalRecordBook = new RecordBook();
    static RecordBook badRecordBook = new RecordBook();
    static RecordBook emptyRecordBook = new RecordBook();
    @BeforeAll
    static void excellentSet() {
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

        excellentRecordBook.setFinalTask(Mark.FIVE);
        excellentRecordBook.addSemester(sem1);
        excellentRecordBook.addSemester(sem2);
    }

    @BeforeAll
    static void normalSet() {
        Subject subj1_2 = new Subject("Russian", Mark.FOUR);
        Subject subj1_1 = new Subject("Russian", Mark.FIVE);
        Subject subj2_1 = new Subject("English", Mark.FOUR);
        Subject subj3_2 = new Subject("Italian", Mark.FIVE);
        Subject subj4_2 = new Subject("French", Mark.THREE);
        Subject subj5_1 = new Subject("German", Mark.FOUR);

        Semester sem1 = new Semester(1);
        Semester sem2 = new Semester(2);

        sem1.addSubject(subj1_1);
        sem1.addSubject(subj2_1);
        sem1.addSubject(subj5_1);
        sem2.addSubject(subj3_2);
        sem2.addSubject(subj4_2);
        sem2.addSubject(subj1_2);


        normalRecordBook.setFinalTask(Mark.FOUR);
        normalRecordBook.addSemester(sem1);
        normalRecordBook.addSemester(sem2);
    }

    @BeforeAll
    static void badSet() {
        Subject subj1_2 = new Subject("Russian", Mark.THREE);
        Subject subj1_1 = new Subject("Russian", Mark.TWO);
        Subject subj2_1 = new Subject("English", Mark.TWO);
        Subject subj3_2 = new Subject("Italian", Mark.THREE);
        Subject subj4_2 = new Subject("French", Mark.FOUR);
        Subject subj5_1 = new Subject("German", Mark.THREE);

        Semester sem1 = new Semester(1);
        Semester sem2 = new Semester(2);

        sem1.addSubject(subj1_1);
        sem1.addSubject(subj2_1);
        sem1.addSubject(subj5_1);
        sem2.addSubject(subj3_2);
        sem2.addSubject(subj4_2);
        sem2.addSubject(subj1_2);

        badRecordBook.setFinalTask(Mark.THREE);
        badRecordBook.addSemester(sem1);
        badRecordBook.addSemester(sem2);

    }



    @ParameterizedTest
    @MethodSource("isRedDiploma")
    void testIsRedDiploma(RecordBook recordBook, boolean actualVal) {
        Assertions.assertEquals(recordBook.isRedDiploma(), actualVal);
    }

    @ParameterizedTest
    @MethodSource("isIncScholarship")
    void testIsIncScholarship1(RecordBook recordBook, int numberSemester, boolean actualVal) {
        Assertions.assertEquals(recordBook.getSemesterByNumber(numberSemester).isIncScholarship(), actualVal);
    }


    @ParameterizedTest
    @MethodSource("averageMark")
    void testAverageMark(RecordBook recordBook, Double avMark) {
        Assertions.assertEquals(recordBook.getAverageMark(), avMark);
    }


    private static Stream<Arguments> averageMark() {
        return Stream.of(
                Arguments.of(excellentRecordBook, 4.9),
                Arguments.of(normalRecordBook, 4.2),
                Arguments.of(badRecordBook, 2.9),
                Arguments.of(emptyRecordBook, 0.0)
        );
    }

    private static Stream<Arguments> isIncScholarship() {
        return Stream.of(
                Arguments.of(excellentRecordBook, 1, true),
                Arguments.of(normalRecordBook, 1, false),
                Arguments.of(badRecordBook, 1, false),
                Arguments.of(excellentRecordBook, 2, false),
                Arguments.of(normalRecordBook, 2, false),
                Arguments.of(badRecordBook, 2, false)
        );
    }

    private static Stream<Arguments> isRedDiploma() {
        return Stream.of(
                Arguments.of(excellentRecordBook, true),
                Arguments.of(normalRecordBook, false),
                Arguments.of(badRecordBook, false),
                Arguments.of(emptyRecordBook, false)
        );
    }

}