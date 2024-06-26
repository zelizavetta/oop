package nsu.fit.ezaitseva.model.entity.attendance;

import nsu.fit.ezaitseva.model.entity.common.GitConfig;
import nsu.fit.ezaitseva.model.entity.fixes.StudentInformation;
import nsu.fit.ezaitseva.model.git.PersonGit;
import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * The type Attendance checker.
 */
public class AttendanceChecker {

    /**
     * Check attendance map.
     *
     * @param gitConfig           the git config
     * @param studentInformations the student informations
     * @param workingDir          the working dir
     * @param lessonList          the lesson list
     * @return the map
     */
    public static Map<String, Map<Lesson, Boolean>> checkAttendance(
            GitConfig gitConfig, Collection<StudentInformation> studentInformations,
            File workingDir, Collection<Lesson> lessonList) {
        Map<String, Map<Lesson, Boolean>> studentAttendance = new HashMap<>();
        for (StudentInformation studentInfo : studentInformations) {
            studentAttendance.put(studentInfo.getStudentConfig().getGitName(),
                    checkAttendance(gitConfig,
                            studentInfo,
                            workingDir, lessonList)
            );
        }
        return studentAttendance;
    }

    /**
     * Check attendance map.
     *
     * @param gitConfig   the git config
     * @param studentInfo the student info
     * @param workingDir  the working dir
     * @param lessonList  the lesson list
     * @return the map
     */
    public static Map<Lesson, Boolean> checkAttendance(
            GitConfig gitConfig, StudentInformation studentInfo,
            File workingDir, Collection<Lesson> lessonList) {
        Map<Lesson, Boolean> studentAttendance = new HashMap<>();

        for (Lesson lesson : lessonList) {
            boolean isLessonAttended = studentInfo.getStudentAttendance().contains(lesson);
            studentAttendance.put(lesson, isLessonAttended);
        }
        return studentAttendance;
    }

    private static boolean checkStudentAttendance(PersonGit studentGit, Lesson lesson) {
        try {
            System.out.println("lesson " + lesson);
            Iterable<RevCommit> commits = studentGit.getCommits();
            for (RevCommit commit : commits) {
                Date authorDate = commit.getAuthorIdent().getWhen();

                if (authorDate.equals(lesson.date())) {
                    return true;
                }
            }
        } catch (GitAPIException e) {
            System.err.println(e);
        }
        return false;
    }
}