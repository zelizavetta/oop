package nsu.fit.ezaitseva.model.entity.fixes;

import lombok.Data;
import nsu.fit.ezaitseva.model.entity.attendance.Lesson;
import nsu.fit.ezaitseva.model.entity.group.StudentConfig;
import nsu.fit.ezaitseva.model.entity.tasks.TaskConfig;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Data
public class StudentInformation {
    StudentConfig studentConfig;
    Map<String, String> branchRename = new HashMap<>();
    Map<String, String> folderRename = new HashMap<>();
    Map<String, Double> extraScore = new HashMap<>();
    String branchPattern;
    String folderPattern;

    Set<Lesson> studentAttendance = new LinkedHashSet<>();

    public StudentInformation(StudentConfig studentConfig) {
        this.studentConfig = studentConfig;
    }

    public StudentInformation(StudentConfig studentConfig, TaskConfig taskConfig) {
        this(studentConfig);
        branchPattern = taskConfig.getBranchPattern();
        folderPattern = taskConfig.getFolderPattern();
        taskConfig.getTasks().forEach(task -> {
            extraScore.put(task.id(), 0.0);
        });
    }
}