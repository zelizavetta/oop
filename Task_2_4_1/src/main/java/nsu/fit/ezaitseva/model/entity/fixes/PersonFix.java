package nsu.fit.ezaitseva.model.entity.fixes;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class PersonFix {
    StudentInformation studentInformation;

    public PersonFix(StudentInformation studentInformation) {
        this.studentInformation = studentInformation;
    }

    public void changeBranch(String taskId, String newBranchName) {
        if (!studentInformation.extraScore.containsKey(taskId)) {
            System.err.println("No such taskId: " + taskId);
            System.err.println(studentInformation.branchRename);
        } else {
            studentInformation.branchRename.put(taskId, newBranchName);
        }

    }

    public void changeFolder(String taskId, String newFolderName) {
        if (!studentInformation.extraScore.containsKey(taskId)) {
            System.err.println("No such taskId: " + taskId);
        } else {
            studentInformation.folderRename.put(taskId, newFolderName);
        }

    }

    public void changeBranch(Map<String, String> changes) {
        changes.forEach(this::changeBranch);
    }

    public void changeFolder(Map<String, String> changes) {
        changes.forEach(this::changeFolder);
    }

    public void changeBranchPattern(String newPattern) {
        studentInformation.branchPattern = newPattern;
    }

    public void changeFolderPattern(String newPattern) {
        System.out.println("asdasf: " + studentInformation.folderPattern + " to " + newPattern
                + " for " + studentInformation.studentConfig.getGitName());

        studentInformation.folderPattern = newPattern;
    }

    public void changeExtraScore(String taskId, BigDecimal score) {
        if (!studentInformation.extraScore.containsKey(taskId)) {
            System.out.println(studentInformation.extraScore);
            System.err.println("No such taskId: " + taskId);
        } else {
            studentInformation.extraScore.put(taskId, score.doubleValue());
        }

    }

    public void changeExtraScore(Map<String, BigDecimal> changes) {
        changes.forEach(this::changeExtraScore);
    }
}