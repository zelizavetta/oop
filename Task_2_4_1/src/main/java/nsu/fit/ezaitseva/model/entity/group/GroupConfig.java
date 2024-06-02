package nsu.fit.ezaitseva.model.entity.group;

import groovy.lang.Closure;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsu.fit.ezaitseva.model.dsl.Delegator;
import nsu.fit.ezaitseva.model.entity.common.GeneralConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Group config.
 */
@Data
@NoArgsConstructor
public class GroupConfig {
    /**
     * The Group name.
     */
    String groupName;
    /**
     * The Default branch.
     */
    String defaultBranch = "main";
    /**
     * The Default repository.
     */
    String defaultRepository = "oop";
    /**
     * The Student configs.
     */
    List<StudentConfig> studentConfigs = new ArrayList<>();

    /**
     * Instantiates a new Group config.
     *
     * @param generalConfig the general config
     */
    public GroupConfig(GeneralConfig generalConfig) {
        defaultBranch = generalConfig.getGit().getDefaultBranch();
        defaultRepository = generalConfig.getGit().getDefaultRepository();
    }

    /**
     * Group.
     *
     * @param groupName the group name
     * @param closure   the closure
     */
    public void group(String groupName, Closure<?> closure) {
        this.groupName = groupName;
        Delegator.groovyDelegate(this, closure);
    }

    private StudentConfig getDefaultStudent() {
        StudentConfig studentConfig = new StudentConfig();
        studentConfig.defaultBranch = defaultBranch;
        studentConfig.repository = defaultRepository;
        return studentConfig;
    }

    /**
     * Student.
     *
     * @param closure the closure
     */
    public void student(Closure<?> closure) {
        StudentConfig studentConfig = getDefaultStudent();
        Delegator.groovyDelegate(studentConfig, closure);
        studentConfigs.add(studentConfig);
    }


    /**
     * Student.
     *
     * @param gitName the git name
     * @param closure the closure
     */
    public void student(String gitName, Closure<?> closure) {
        StudentConfig studentConfig = getDefaultStudent();
        studentConfig.gitName = gitName;
        studentConfig.fullName = gitName;
        Delegator.groovyDelegate(studentConfig, closure);
        studentConfigs.add(studentConfig);
    }

    /**
     * Student.
     *
     * @param gitName  the git name
     * @param fullName the full name
     * @param closure  the closure
     */
    public void student(String gitName,
                        String fullName,
                        Closure<?> closure) {
        StudentConfig studentConfig = getDefaultStudent();
        studentConfig.gitName = gitName;
        studentConfig.fullName = fullName;
        Delegator.groovyDelegate(studentConfig, closure);
        studentConfigs.add(studentConfig);
    }


    /**
     * Student.
     *
     * @param gitName the git name
     */
    public void student(String gitName) {
        StudentConfig studentConfig = getDefaultStudent();
        studentConfig.fullName = gitName;
        studentConfig.gitName = gitName;
        studentConfigs.add(studentConfig);
    }

    /**
     * Student.
     *
     * @param gitName  the git name
     * @param fullName the full name
     */
    public void student(String gitName,
                        String fullName) {
        StudentConfig studentConfig = getDefaultStudent();
        studentConfig.gitName = gitName;
        studentConfig.fullName = fullName;
        studentConfigs.add(studentConfig);
    }


}