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

    static RecordBook excellentRecordBook1 = new RecordBook();
    static RecordBook normalRecordBook1 = new RecordBook();
    static RecordBook badRecordBook1 = new RecordBook();
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

        excellentRecordBook1.setFinalTask(Mark.FIVE);
        excellentRecordBook1.addSemester(sem1);
        excellentRecordBook1.addSemester(sem2);
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


        normalRecordBook1.setFinalTask(Mark.FOUR);
        normalRecordBook1.addSemester(sem1);
        normalRecordBook1.addSemester(sem2);
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

        badRecordBook1.setFinalTask(Mark.THREE);
        badRecordBook1.addSemester(sem1);
        badRecordBook1.addSemester(sem2);

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
    void testAverageMark(RecordBook recordBook, double avMark) {
        Assertions.assertEquals(recordBook.getAverageMark(), avMark);
    }


    private static Stream<Arguments> averageMark() {
        return Stream.of(
                Arguments.of(excellentRecordBook1, 4.9),
                Arguments.of(normalRecordBook1, 4.2),
                Arguments.of(badRecordBook1, 2.9)
        );
    }

    private static Stream<Arguments> isIncScholarship() {
        return Stream.of(
                Arguments.of(excellentRecordBook1, 1, true),
                Arguments.of(normalRecordBook1, 1, false),
                Arguments.of(badRecordBook1, 1, false),
                Arguments.of(excellentRecordBook1, 2, false),
                Arguments.of(normalRecordBook1, 2, false),
                Arguments.of(badRecordBook1, 2, false)
        );
    }

    private static Stream<Arguments> isRedDiploma() {
        return Stream.of(
                Arguments.of(excellentRecordBook1, true),
                Arguments.of(normalRecordBook1, false),
                Arguments.of(badRecordBook1, false)
        );
    }

}