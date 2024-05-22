package nsu.fit.ezaitseva.model.entity.attendance;

import lombok.Data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class LessonsConfig {
    private final Set<Lesson> lessonList = new LinkedHashSet<>();
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public void lesson(String dateString) {

        try {
            Lesson lesson = new Lesson(dateFormat.parse(dateString));
            lessonList.add(lesson);
            System.out.println(lesson);
        } catch (ParseException e) {
            System.err.println("Problem with lesson date: " + dateString);
        }
    }

    public void lessons(String startDateString, String stopDateString, int interval) {

        Date startDate;
        try {
            startDate = dateFormat.parse(startDateString);
        } catch (ParseException e) {
            System.err.println(e);
            return;
        }

        Date stopDate;
        try {
            stopDate = dateFormat.parse(stopDateString);
        } catch (ParseException e) {
            System.err.println(e);
            return;
        }
        if (interval <= 0) {
            System.err.println("Wrong interval: " + interval);
            return;
        }
        Instant tmpInstant = startDate.toInstant();
        Instant stopInstant = stopDate.toInstant();

        while (tmpInstant.isBefore(stopInstant)) {
            lessonList.add(new Lesson(Date.from(tmpInstant)));
            tmpInstant = tmpInstant.plusSeconds(interval * 86400L);
        }

    }

}