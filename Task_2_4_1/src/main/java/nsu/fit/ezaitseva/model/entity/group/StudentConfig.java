package nsu.fit.ezaitseva.model.entity.group;

import lombok.Data;

@Data
public class StudentConfig {
    String fullName;
    String gitName;
    String defaultBranch;
    String repository;

    public StudentConfig() {

    }

    public StudentConfig(String gitName) {
        this.gitName = gitName;
    }

    public StudentConfig(String gitName, String fullName) {
        this.gitName = gitName;
        this.fullName = fullName;
    }

}