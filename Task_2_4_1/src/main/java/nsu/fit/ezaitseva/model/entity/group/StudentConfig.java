package nsu.fit.ezaitseva.model.entity.group;

import lombok.Data;

/**
 * The type Student config.
 */
@Data
public class StudentConfig {
    /**
     * The Full name.
     */
    String fullName;
    /**
     * The Git name.
     */
    String gitName;
    /**
     * The Default branch.
     */
    String defaultBranch;
    /**
     * The Repository.
     */
    String repository;

    /**
     * Instantiates a new Student config.
     */
    public StudentConfig() {

    }

    /**
     * Instantiates a new Student config.
     *
     * @param gitName the git name
     */
    public StudentConfig(String gitName) {
        this.gitName = gitName;
    }

    /**
     * Instantiates a new Student config.
     *
     * @param gitName  the git name
     * @param fullName the full name
     */
    public StudentConfig(String gitName, String fullName) {
        this.gitName = gitName;
        this.fullName = fullName;
    }

}