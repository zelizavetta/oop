package nsu.fit.ezaitseva.model.dsl;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import nsu.fit.ezaitseva.model.entity.attendance.AttendanceConfig;
import nsu.fit.ezaitseva.model.entity.attendance.Lesson;
import nsu.fit.ezaitseva.model.entity.attendance.LessonsConfig;
import nsu.fit.ezaitseva.model.entity.common.GeneralConfig;
import nsu.fit.ezaitseva.model.entity.fixes.FixConfig;
import nsu.fit.ezaitseva.model.entity.fixes.StudentInformation;
import nsu.fit.ezaitseva.model.entity.group.GroupConfig;
import nsu.fit.ezaitseva.model.entity.tasks.TaskConfig;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GroovyParser {
    private final GroovyShell sh;

    public GroovyParser() {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());
        this.sh = new GroovyShell(GroovyParser.class.getClassLoader(), new Binding(), cc);
    }

    public DelegatingScript getScript(File file) throws IOException {
        return (DelegatingScript) sh.parse(file);
    }

    public DelegatingScript getScript(String filePath) throws IOException {
        return getScript(new File(filePath));
    }

    public void parseScript(String filePath, Object delegate) throws IOException {
        DelegatingScript script = getScript(filePath);
        script.setDelegate(delegate);
        script.run();
    }


    public GeneralConfig readGeneral(String scriptPath) {
        GeneralConfig generalConfig = new GeneralConfig();
        try {
            parseScript(scriptPath, generalConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return generalConfig;
    }

    public TaskConfig readTasks(GeneralConfig generalConfig, String scriptPath) {
        TaskConfig taskConfig = new TaskConfig(generalConfig);
        try {
            parseScript(scriptPath, taskConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return taskConfig;

    }

    public GroupConfig readGroup(GeneralConfig generalConfig, String scriptPath) {
        GroupConfig groupConfig = new GroupConfig(generalConfig);
        try {
            parseScript(scriptPath, groupConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return groupConfig;
    }

    public FixConfig readFixes(Map<String, StudentInformation> studentInformation, String scriptPath) {
        FixConfig fixConfig = new FixConfig(studentInformation);
        try {
            parseScript(scriptPath, fixConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fixConfig;
    }

    public LessonsConfig readLessons(String scriptPath) {
        LessonsConfig lessonsConfig = new LessonsConfig();
        try {
            parseScript(scriptPath, lessonsConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lessonsConfig;
    }

    public AttendanceConfig readAttendance(Map<String, StudentInformation> studentInformation,
                                           Set<Lesson> lessons, String scriptPath) {
        AttendanceConfig attendanceConfig = new AttendanceConfig(studentInformation, lessons);
        try {
            parseScript(scriptPath, attendanceConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return attendanceConfig;
    }

    public static Map<String, StudentInformation> getStudentInformationMap(GroupConfig groupConfig,
                                                                           TaskConfig taskConfig) {
        Map<String, StudentInformation> informationMap = new HashMap<>();
        groupConfig.getStudentConfigs()
                .forEach(studentConfig -> informationMap.put(studentConfig.getGitName(),
                        new StudentInformation(studentConfig, taskConfig)
                ));
        return informationMap;
    }
}