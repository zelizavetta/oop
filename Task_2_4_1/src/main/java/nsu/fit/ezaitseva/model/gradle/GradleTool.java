package nsu.fit.ezaitseva.model.gradle;

import lombok.Builder;
import lombok.Singular;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class GradleTool implements AutoCloseable {
    private final File projectFolder;
    private ProjectConnection projectConnection;

    public GradleTool(File projectFolder) {
        this.projectFolder = projectFolder;
        connect();
    }

    public File getProjectFolder() {
        return projectFolder;
    }

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

    public record TaskPair(String task, Runnable onSuccess) {
        TaskPair(String task) {
            this(task, () -> {
            });
        }
    }

    @Builder
    public static class TaskList {
        @Singular
        public final List<TaskPair> taskPairs;
    }
}