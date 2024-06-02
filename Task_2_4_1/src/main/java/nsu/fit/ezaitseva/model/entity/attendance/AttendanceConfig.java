package nsu.fit.ezaitseva.model.entity.attendance;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import lombok.Data;
import nsu.fit.ezaitseva.model.entity.fixes.StudentInformation;

/**
 * The type Attendance config.
 */
@Data
public class AttendanceConfig {

    private final Map<String, StudentInformation> studentInformationMap;
    private final Set<Lesson> lessons;

    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * Instantiates a new Attendance config.
     *
     * @param studentInformationMap the student information map
     * @param lessons               the lessons
     */
    public AttendanceConfig(Map<String, StudentInformation> studentInformationMap, Set<Lesson> lessons) {
        this.studentInformationMap = studentInformationMap;
        this.lessons = lessons;
    }

    /**
     * Attended.
     *
     * @param student    the student
     * @param dateString the date string
     */
    public void attended(String student, String dateString) {
        Date date;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.err.println(e);
            return;
        }
        Lesson lesson = new Lesson(date);
        if (lessons.contains(lesson)) {
            studentInformationMap.get(student).getStudentAttendance().add(lesson);
            System.out.println("added " + lesson);
        }
    }

    /**
     * Attended.
     *
     * @param student         the student
     * @param dateStartString the date start string
     * @param dateEndString   the date end string
     */
    public void attended(String student, String dateStartString, String dateEndString) {

    }
}