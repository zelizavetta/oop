package nsu.fit.ezaitseva.model.evaluator;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import nsu.fit.ezaitseva.model.entity.common.EvaluationConfig;
import nsu.fit.ezaitseva.model.entity.common.GeneralConfig;
import nsu.fit.ezaitseva.model.entity.fixes.StudentInformation;
import nsu.fit.ezaitseva.model.entity.tasks.Task;
import nsu.fit.ezaitseva.model.git.PersonGit;
import nsu.fit.ezaitseva.model.gradle.GradleTool;
import org.eclipse.jgit.api.errors.GitAPIException;


/**
 * The type Student evaluator.
 */
public class StudentEvaluator implements AutoCloseable {
    private final StudentInformation studentInformation;
    private final PersonGit personGit;
    private final EvaluationConfig evaluationConfig;
    private final GradleTool gradleTool;

    /**
     * Instantiates a new Student evaluator.
     *
     * @param studentInformation the student information
     * @param generalDir         the general dir
     * @param generalConfig      the general config
     * @throws GitAPIException the git api exception
     * @throws IOException     the io exception
     */
    public StudentEvaluator(StudentInformation studentInformation, File generalDir,
                            GeneralConfig generalConfig) throws GitAPIException, IOException {
        this.studentInformation = studentInformation;
        File studentDir = new File(generalDir, studentInformation.getStudentConfig().getGitName());
        personGit = new PersonGit(generalConfig.getGit(), studentInformation, studentDir);
        evaluationConfig = generalConfig.getEvaluationConfig();
        gradleTool = new GradleTool(studentDir);
    }

    /**
     * Evaluate task assessment.
     *
     * @param task the task
     * @return the assessment
     * @throws IOException     the io exception
     * @throws GitAPIException the git api exception
     */
    public Assessment evaluateTask(Task task) throws IOException, GitAPIException {
        personGit.switchTaskIfNotExists(task);

        return TaskEvaluator.evaluate(gradleTool, task, studentInformation, evaluationConfig);
    }

    /**
     * Evaluate task map.
     *
     * @param tasks the tasks
     * @return the map
     */
    public Map<String, Assessment> evaluateTask(Collection<Task> tasks) {
        HashMap<String, Assessment> assessments = new HashMap<String, Assessment>();
        tasks.forEach((task -> {
            try {
                assessments.put(task.id(), evaluateTask(task));
            } catch (IOException | GitAPIException e) {
                System.err.println(studentInformation.getStudentConfig().getGitName() + "|"
                        + task.id() + ": " + e);
                assessments.put(task.id(), Assessment.builder().build());
            }
        }));
        return assessments;
    }

    @Override
    public void close() throws Exception {
        gradleTool.close();
        personGit.close();
    }
}