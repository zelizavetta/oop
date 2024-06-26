package nsu.fit.ezaitseva.model.entity.attendance;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Data;

/**
 * The type Lessons config.
 */
@Data
public class LessonsConfig {
    private final Set<Lesson> lessonList = new LinkedHashSet<>();
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * Lesson.
     *
     * @param dateString the date string
     */
    public void lesson(String dateString) {

        try {
            Lesson lesson = new Lesson(dateFormat.parse(dateString));
            lessonList.add(lesson);
            System.out.println(lesson);
        } catch (ParseException e) {
            System.err.println("Problem with lesson date: " + dateString);
        }
    }

    /**
     * Lessons.
     *
     * @param startDateString the start date string
     * @param stopDateString  the stop date string
     * @param interval        the interval
     */
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