package nsu.fit.ezaitseva.model.entity.group;

import groovy.lang.Closure;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsu.fit.ezaitseva.model.dsl.Delegator;
import nsu.fit.ezaitseva.model.entity.common.GeneralConfig;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class GroupConfig {
    String groupName;
    String defaultBranch = "main";
    String defaultRepository = "oop";
    List<StudentConfig> studentConfigs = new ArrayList<>();

    public GroupConfig(GeneralConfig generalConfig) {
        defaultBranch = generalConfig.getGit().getDefaultBranch();
        defaultRepository = generalConfig.getGit().getDefaultRepository();
    }

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

    public void student(Closure<?> closure) {
        StudentConfig studentConfig = getDefaultStudent();
        Delegator.groovyDelegate(studentConfig, closure);
        studentConfigs.add(studentConfig);
    }


    public void student(String gitName, Closure<?> closure) {
        StudentConfig studentConfig = getDefaultStudent();
        studentConfig.gitName = gitName;
        studentConfig.fullName = gitName;
        Delegator.groovyDelegate(studentConfig, closure);
        studentConfigs.add(studentConfig);
    }

    public void student(String gitName,
                        String fullName,
                        Closure<?> closure) {
        StudentConfig studentConfig = getDefaultStudent();
        studentConfig.gitName = gitName;
        studentConfig.fullName = fullName;
        Delegator.groovyDelegate(studentConfig, closure);
        studentConfigs.add(studentConfig);
    }


    public void student(String gitName) {
        StudentConfig studentConfig = getDefaultStudent();
        studentConfig.fullName = gitName;
        studentConfig.gitName = gitName;
        studentConfigs.add(studentConfig);
    }

    public void student(String gitName,
                        String fullName) {
        StudentConfig studentConfig = getDefaultStudent();
        studentConfig.gitName = gitName;
        studentConfig.fullName = fullName;
        studentConfigs.add(studentConfig);
    }


}