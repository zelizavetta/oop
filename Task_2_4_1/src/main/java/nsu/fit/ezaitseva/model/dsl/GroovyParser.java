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

/**
 * The type Groovy parser.
 */
public class GroovyParser {
    private final GroovyShell sh;

    /**
     * Instantiates a new Groovy parser.
     */
    public GroovyParser() {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());
        this.sh = new GroovyShell(GroovyParser.class.getClassLoader(), new Binding(), cc);
    }

    /**
     * Gets script.
     *
     * @param file the file
     * @return the script
     * @throws IOException the io exception
     */
    public DelegatingScript getScript(File file) throws IOException {
        return (DelegatingScript) sh.parse(file);
    }

    /**
     * Gets script.
     *
     * @param filePath the file path
     * @return the script
     * @throws IOException the io exception
     */
    public DelegatingScript getScript(String filePath) throws IOException {
        return getScript(new File(filePath));
    }

    /**
     * Parse script.
     *
     * @param filePath the file path
     * @param delegate the delegate
     * @throws IOException the io exception
     */
    public void parseScript(String filePath, Object delegate) throws IOException {
        DelegatingScript script = getScript(filePath);
        script.setDelegate(delegate);
        script.run();
    }


    /**
     * Read general general config.
     *
     * @param scriptPath the script path
     * @return the general config
     */
    public GeneralConfig readGeneral(String scriptPath) {
        GeneralConfig generalConfig = new GeneralConfig();
        try {
            parseScript(scriptPath, generalConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return generalConfig;
    }

    /**
     * Read tasks task config.
     *
     * @param generalConfig the general config
     * @param scriptPath    the script path
     * @return the task config
     */
    public TaskConfig readTasks(GeneralConfig generalConfig, String scriptPath) {
        TaskConfig taskConfig = new TaskConfig(generalConfig);
        try {
            parseScript(scriptPath, taskConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return taskConfig;

    }

    /**
     * Read group group config.
     *
     * @param generalConfig the general config
     * @param scriptPath    the script path
     * @return the group config
     */
    public GroupConfig readGroup(GeneralConfig generalConfig, String scriptPath) {
        GroupConfig groupConfig = new GroupConfig(generalConfig);
        try {
            parseScript(scriptPath, groupConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return groupConfig;
    }

    /**
     * Read fixes fix config.
     *
     * @param studentInformation the student information
     * @param scriptPath         the script path
     * @return the fix config
     */
    public FixConfig readFixes(Map<String, StudentInformation> studentInformation, String scriptPath) {
        FixConfig fixConfig = new FixConfig(studentInformation);
        try {
            parseScript(scriptPath, fixConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fixConfig;
    }

    /**
     * Read lessons lessons config.
     *
     * @param scriptPath the script path
     * @return the lessons config
     */
    public LessonsConfig readLessons(String scriptPath) {
        LessonsConfig lessonsConfig = new LessonsConfig();
        try {
            parseScript(scriptPath, lessonsConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lessonsConfig;
    }

    /**
     * Read attendance attendance config.
     *
     * @param studentInformation the student information
     * @param lessons            the lessons
     * @param scriptPath         the script path
     * @return the attendance config
     */
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

    /**
     * Gets student information map.
     *
     * @param groupConfig the group config
     * @param taskConfig  the task config
     * @return the student information map
     */
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