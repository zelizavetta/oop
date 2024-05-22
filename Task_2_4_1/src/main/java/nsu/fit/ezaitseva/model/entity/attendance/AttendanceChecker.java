package nsu.fit.ezaitseva.model.entity.attendance;

import nsu.fit.ezaitseva.model.entity.common.GitConfig;
import nsu.fit.ezaitseva.model.entity.fixes.StudentInformation;
import nsu.fit.ezaitseva.model.git.PersonGit;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AttendanceChecker {

    public static Map<String, Map<Lesson, Boolean>> checkAttendance
            (GitConfig gitConfig, Collection<StudentInformation> studentInformations,
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

    public static Map<Lesson, Boolean> checkAttendance(
            GitConfig gitConfig, StudentInformation studentInfo, File workingDir, Collection<Lesson> lessonList) {
        Map<Lesson, Boolean> studentAttendance = new HashMap<>();

        for (Lesson lesson : lessonList) {
            boolean isLessonAttended = studentInfo.getStudentAttendance().contains(lesson);

//            if (true || !isLessonAttended) {
//                try (PersonGit personGit = new PersonGit(gitConfig, studentInfo, new File(workingDir,
//                        studentInfo.getStudentConfig().getGitName()))) {
//                    isLessonAttended = checkStudentAttendance(personGit, lesson);
//
//                } catch (Exception e) {
//                    System.err.println("gitException" + e);
//                }
//            }
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

                if (authorDate.equals(lesson.date())) return true;
            }
        } catch (GitAPIException e) {
            System.err.println(e);
        }
        return false;
    }
}