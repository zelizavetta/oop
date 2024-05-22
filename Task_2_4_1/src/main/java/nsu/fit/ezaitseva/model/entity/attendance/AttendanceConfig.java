package nsu.fit.ezaitseva.model.entity.attendance;

import lombok.Data;
import nsu.fit.ezaitseva.model.entity.fixes.StudentInformation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class AttendanceConfig {

    private final Map<String, StudentInformation> studentInformationMap;
    private final Set<Lesson> lessons;

    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public AttendanceConfig(Map<String, StudentInformation> studentInformationMap, Set<Lesson> lessons) {
        this.studentInformationMap = studentInformationMap;
        this.lessons = lessons;
    }

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

    public void attended(String student, String dateStartString, String dateEndString) {

    }
}