package nsu.fit.ezaitseva.model.entity.fixes;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import nsu.fit.ezaitseva.model.entity.attendance.Lesson;
import nsu.fit.ezaitseva.model.entity.group.StudentConfig;
import nsu.fit.ezaitseva.model.entity.tasks.TaskConfig;
import lombok.Data;

/**
 * The type Student information.
 */
@Data
public class StudentInformation {
    /**
     * The Student config.
     */
    StudentConfig studentConfig;
    /**
     * The Branch rename.
     */
    Map<String, String> branchRename = new HashMap<>();
    /**
     * The Folder rename.
     */
    Map<String, String> folderRename = new HashMap<>();
    /**
     * The Extra score.
     */
    Map<String, Double> extraScore = new HashMap<>();
    /**
     * The Branch pattern.
     */
    String branchPattern;
    /**
     * The Folder pattern.
     */
    String folderPattern;

    /**
     * The Student attendance.
     */
    Set<Lesson> studentAttendance = new LinkedHashSet<>();

    /**
     * Instantiates a new Student information.
     *
     * @param studentConfig the student config
     */
    public StudentInformation(StudentConfig studentConfig) {
        this.studentConfig = studentConfig;
    }

    /**
     * Instantiates a new Student information.
     *
     * @param studentConfig the student config
     * @param taskConfig    the task config
     */
    public StudentInformation(StudentConfig studentConfig, TaskConfig taskConfig) {
        this(studentConfig);
        branchPattern = taskConfig.getBranchPattern();
        folderPattern = taskConfig.getFolderPattern();
        taskConfig.getTasks().forEach(task -> {
            extraScore.put(task.id(), 0.0);
        });
    }
}