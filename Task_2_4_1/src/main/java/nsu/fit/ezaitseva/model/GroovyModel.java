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

public class GroovyModel {
    public final GroovyParser groovyParser = new GroovyParser();
    public final GeneralConfig generalConfig;
    public final GroupConfig group;
    public final TaskConfig taskConfig;
    public final Map<String, StudentInformation> studentInformationMap;
    public final FixConfig fixes;
    public final AttendanceConfig attendanceConfig;
    public final LessonsConfig lessonsConfig;

    public final File generalDir;

    public GroovyModel(File scriptsDir, File generalDir) {
        this.generalDir = generalDir;
        String scriptPath = scriptsDir.getPath() + "/";
        generalConfig = groovyParser.readGeneral(scriptPath + "general.groovy");
        taskConfig = groovyParser.readTasks(generalConfig, scriptPath + "tasks.groovy");
        group = groovyParser.readGroup(generalConfig, scriptPath + "group22214.groovy");
        fixes = groovyParser.readFixes(studentInformationMap = GroovyParser.getStudentInformationMap(group, taskConfig),
                scriptPath + "fixes.groovy");
        lessonsConfig = groovyParser.readLessons(scriptPath +"lessons.groovy");

        attendanceConfig = groovyParser.readAttendance(studentInformationMap, lessonsConfig.getLessonList(),
                scriptPath + "attendance.groovy");

    }

    public Map<String, Map<String, Assessment>> evaluateAll() {
        return evaluatePersons(studentInformationMap.keySet());
    }

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

    public Map<String, Assessment> evaluatePerson(String gitName) {
        return evaluatePerson(studentInformationMap.get(gitName));
    }

    public Map<String, Map<String, Assessment>> evaluatePersons(Collection<String> gitNames) {
        Map<String, Map<String, Assessment>> studentResults = new HashMap<>();
        for (String studentGit : gitNames) {
            studentResults.put(studentGit, evaluatePerson(studentInformationMap.get(studentGit)));
        }
        return studentResults;
    }
}