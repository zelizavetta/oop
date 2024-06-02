package nsu.fit.ezaitseva.model.entity.tasks;

import java.util.List;
import lombok.Data;


/**
 * The type Task.
 */
@Data
public class Task {
    /**
     * The Name.
     */
    String name;
    /**
     * Module name.
     */
    String folder;
    /**
     * The Branch.
     */
    String branch;
    /**
     * The Points.
     */
    Double points;
    /**
     * The Run tests.
     */
    Boolean runTests = true;
    /**
     * The Numbers.
     */
    List<Integer> numbers;

    private Task(String name, String folder, String branch) {
        this.name = name;
        this.folder = folder;
        this.branch = branch;
    }

    /**
     * Instantiates a new Task.
     *
     * @param name   the name
     * @param folder the folder
     * @param branch the branch
     * @param points the points
     */
    public Task(String name, String folder,
                String branch, Double points) {
        this(name, folder, branch);
        this.points = points;
    }

    /**
     * Id string.
     *
     * @return the string
     */
    public String id() {
        return folder;
    }
}