package nsu.fit.ezaitseva.model;

import nsu.fit.ezaitseva.model.dsl.GroovyParser;
import nsu.fit.ezaitseva.model.entity.attendance.AttendanceConfig;
import nsu.fit.ezaitseva.model.entity.attendance.LessonsConfig;
import nsu.fit.ezaitseva.model.entity.common.GeneralConfig;
import nsu.fit.ezaitseva.model.entity.fixes.FixConfig;
import nsu.fit.ezaitseva.model.entity.fixes.StudentInformation;
import nsu.fit.ezaitseva.model.entity.group.GroupConfig;
import nsu.fit.ezaitseva.model.entity.tasks.TaskConfig;
import nsu.fit.ezaitseva.model.evaluator.Assessment;
import nsu.fit.ezaitseva.model.evaluator.StudentEvaluator;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Groovy model.
 */
public class GroovyModel {
    /**
     * The Groovy parser.
     */
    public final GroovyParser groovyParser = new GroovyParser();
    /**
     * The General config.
     */
    public final GeneralConfig generalConfig;
    /**
     * The Group.
     */
    public final GroupConfig group;
    /**
     * The Task config.
     */
    public final TaskConfig taskConfig;
    /**
     * The Student information map.
     */
    public final Map<String, StudentInformation> studentInformationMap;
    /**
     * The Fixes.
     */
    public final FixConfig fixes;
    /**
     * The Attendance config.
     */
    public final AttendanceConfig attendanceConfig;
    /**
     * The Lessons config.
     */
    public final LessonsConfig lessonsConfig;

    /**
     * The General dir.
     */
    public final File generalDir;

    /**
     * Instantiates a new Groovy model.
     *
     * @param scriptsDir the scripts dir
     * @param generalDir the general dir
     */
    public GroovyModel(File scriptsDir, File generalDir) {
        this.generalDir = generalDir;
        String scriptPath = scriptsDir.getPath() + "/";
        generalConfig = groovyParser.readGeneral(scriptPath + "general.groovy");
        taskConfig = groovyParser.readTasks(generalConfig, scriptPath + "tasks.groovy");
        group = groovyParser.readGroup(generalConfig, scriptPath + "group22214.groovy");
        fixes = groovyParser.readFixes(studentInformationMap = GroovyParser.getStudentInformationMap(group, taskConfig),
                scriptPath + "fixes.groovy");
        lessonsConfig = groovyParser.readLessons(scriptPath + "lessons.groovy");

        attendanceConfig = groovyParser.readAttendance(studentInformationMap, lessonsConfig.getLessonList(),
                scriptPath + "attendance.groovy");

    }

    /**
     * Evaluate all map.
     *
     * @return the map
     */
    public Map<String, Map<String, Assessment>> evaluateAll() {
        return evaluatePersons(studentInformationMap.keySet());
    }

    /**
     * Evaluate person map.
     *
     * @param studentInfo the student info
     * @return the map
     */
    public Map<String, Assessment> evaluatePerson(StudentInformation studentInfo) {
        try {
            try (StudentEvaluator studentEvaluator = new StudentEvaluator(studentInfo, generalDir,
                    generalConfig)) {
                return studentEvaluator.evaluateTask(taskConfig.getTasks());
            }
        } catch (GitAPIException e) {
            System.err.println(studentInfo.getStudentConfig().getGitName() + ": " + e);
        } catch (IOException e) {
            System.err.println(studentInfo.getStudentConfig().getGitName() + ": " + e);
        } catch (Exception e) {
            System.err.println(studentInfo.getStudentConfig().getGitName() + ": " + e);
        }
        Map<String, Assessment> assessmentMap = new HashMap<>();
        taskConfig.getTasks().forEach((task -> {
            assessmentMap.put(task.id(), Assessment.builder().build());
        }));
        return assessmentMap;
    }

    /**
     * Evaluate person map.
     *
     * @param gitName the git name
     * @return the map
     */
    public Map<String, Assessment> evaluatePerson(String gitName) {
        return evaluatePerson(studentInformationMap.get(gitName));
    }

    /**
     * Evaluate persons map.
     *
     * @param gitNames the git names
     * @return the map
     */
    public Map<String, Map<String, Assessment>> evaluatePersons(Collection<String> gitNames) {
        Map<String, Map<String, Assessment>> studentResults = new HashMap<>();
        for (String studentGit : gitNames) {
            studentResults.put(studentGit, evaluatePerson(studentInformationMap.get(studentGit)));
        }
        return studentResults;
    }
}