package nsu.fit.ezaitseva.model.entity.tasks;

import lombok.Data;

import java.util.List;


@Data
public class Task {
    String name;
    /**
     * Module name.
     */
    String folder;
    String branch;
    Double points;
    Boolean runTests = true;
    List<Integer> numbers;

    private Task(String name, String folder, String branch) {
        this.name = name;
        this.folder = folder;
        this.branch = branch;
    }

    public Task(String name, String folder,
                String branch, Double points) {
        this(name, folder, branch);
        this.points = points;
    }

    public String id() {
        return folder;
    }
}