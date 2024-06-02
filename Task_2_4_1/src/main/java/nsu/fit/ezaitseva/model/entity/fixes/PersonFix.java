package nsu.fit.ezaitseva.model.entity.fixes;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * The type Person fix.
 */
@Data
public class PersonFix {
    /**
     * The Student information.
     */
    StudentInformation studentInformation;

    /**
     * Instantiates a new Person fix.
     *
     * @param studentInformation the student information
     */
    public PersonFix(StudentInformation studentInformation) {
        this.studentInformation = studentInformation;
    }

    /**
     * Change branch.
     *
     * @param taskId        the task id
     * @param newBranchName the new branch name
     */
    public void changeBranch(String taskId, String newBranchName) {
        if (!studentInformation.extraScore.containsKey(taskId)) {
            System.err.println("No such taskId: " + taskId);
            System.err.println(studentInformation.branchRename);
        } else {
            studentInformation.branchRename.put(taskId, newBranchName);
        }

    }

    /**
     * Change folder.
     *
     * @param taskId        the task id
     * @param newFolderName the new folder name
     */
    public void changeFolder(String taskId, String newFolderName) {
        if (!studentInformation.extraScore.containsKey(taskId)) {
            System.err.println("No such taskId: " + taskId);
        } else {
            studentInformation.folderRename.put(taskId, newFolderName);
        }

    }

    /**
     * Change branch.
     *
     * @param changes the changes
     */
    public void changeBranch(Map<String, String> changes) {
        changes.forEach(this::changeBranch);
    }

    /**
     * Change folder.
     *
     * @param changes the changes
     */
    public void changeFolder(Map<String, String> changes) {
        changes.forEach(this::changeFolder);
    }

    /**
     * Change branch pattern.
     *
     * @param newPattern the new pattern
     */
    public void changeBranchPattern(String newPattern) {
        studentInformation.branchPattern = newPattern;
    }

    /**
     * Change folder pattern.
     *
     * @param newPattern the new pattern
     */
    public void changeFolderPattern(String newPattern) {
        System.out.println("asdasf: " + studentInformation.folderPattern + " to " + newPattern
                + " for " + studentInformation.studentConfig.getGitName());

        studentInformation.folderPattern = newPattern;
    }

    /**
     * Change extra score.
     *
     * @param taskId the task id
     * @param score  the score
     */
    public void changeExtraScore(String taskId, BigDecimal score) {
        if (!studentInformation.extraScore.containsKey(taskId)) {
            System.out.println(studentInformation.extraScore);
            System.err.println("No such taskId: " + taskId);
        } else {
            studentInformation.extraScore.put(taskId, score.doubleValue());
        }

    }

    /**
     * Change extra score.
     *
     * @param changes the changes
     */
    public void changeExtraScore(Map<String, BigDecimal> changes) {
        changes.forEach(this::changeExtraScore);
    }
}