package nsu.fit.ezaitseva.model.gradle;

import lombok.Builder;
import lombok.Singular;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * The type Gradle tool.
 */
public class GradleTool implements AutoCloseable {
    private final File projectFolder;
    private ProjectConnection projectConnection;

    /**
     * Instantiates a new Gradle tool.
     *
     * @param projectFolder the project folder
     */
    public GradleTool(File projectFolder) {
        this.projectFolder = projectFolder;
        connect();
    }

    /**
     * Gets project folder.
     *
     * @return the project folder
     */
    public File getProjectFolder() {
        return projectFolder;
    }

    /**
     * Connect.
     */
    public void connect() {
        projectConnection = GradleConnector.newConnector()
                .forProjectDirectory(projectFolder)
                .useGradleVersion("7.4")
                .connect();

    }

    /**
     * Return success tasks
     *
     * @param taskPairs tasks
     * @return success tasks
     */
    public List<String> runTask(Collection<TaskPair> taskPairs) {
        List<String> successTasks = new ArrayList<>();
        taskPairs.forEach((taskPair -> {
            if (runTask(taskPair)) {
                successTasks.add(taskPair.task);
            }
        }));
        return successTasks;
    }

    private boolean runTask(String task, Runnable onSuccess) {
        try {
            projectConnection.newBuild()
                    .forTasks(task)
                    .run();
        } catch (Exception e) {
            System.err.printf("Gradle task %s failed; cause: %s\n", task, e.getMessage());
            return false;
        }
        onSuccess.run();
        return true;
    }

    private boolean runTask(TaskPair taskPair) {
        return runTask(taskPair.task, taskPair.onSuccess);
    }

    @Override
    public void close() throws Exception {
        projectConnection.close();
    }

    /**
     * The type Task pair.
     */
    public record TaskPair(String task, Runnable onSuccess) {
        /**
         * Instantiates a new Task pair.
         *
         * @param task the task
         */
        TaskPair(String task) {
            this(task, () -> {
            });
        }
    }

    /**
     * The type Task list.
     */
    @Builder
    public static class TaskList {
        /**
         * The Task pairs.
         */
        @Singular
        public final List<TaskPair> taskPairs;
    }
}