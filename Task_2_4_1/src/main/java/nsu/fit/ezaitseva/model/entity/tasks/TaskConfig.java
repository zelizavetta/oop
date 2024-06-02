package nsu.fit.ezaitseva.model.entity.tasks;

import groovy.lang.Closure;
import lombok.Data;
import nsu.fit.ezaitseva.model.dsl.Delegator;
import nsu.fit.ezaitseva.model.entity.common.GeneralConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Task config.
 */
@Data
public class TaskConfig {
    /**
     * The Tasks.
     */
    List<Task> tasks = new ArrayList<>();
    /**
     * The Task score.
     */
    Double taskScore;
    private String branchPattern = "task-$1-$2-$3";
    private String folderPattern = "Task_$1_$2_$3";

    /**
     * Instantiates a new Task config.
     *
     * @param generalConfig the general config
     */
    public TaskConfig(GeneralConfig generalConfig) {
        taskScore = generalConfig.getEvaluationConfig().getTaskScore();
    }

    /**
     * Tasks.
     *
     * @param closure the closure
     */
    public void tasks(Closure<?> closure) {
        Delegator.groovyDelegate(this, closure);
    }

    /**
     * Create task.
     *
     * @param name the name
     * @param fst  the fst
     * @param snd  the snd
     * @param thd  the thd
     */
    public void createTask(String name, Integer fst, Integer snd,
                           Integer thd) {
        String branchName = branchPattern
                .replace("$1", fst.toString())
                .replace("$2", snd.toString())
                .replace("$3", thd.toString());
        String folderName = folderPattern
                .replace("$1", fst.toString())
                .replace("$2", snd.toString())
                .replace("$3", thd.toString());
        Task tsk = task(name, folderName, branchName);
        tsk.numbers = List.of(fst, snd, thd);
    }

    /**
     * Create task.
     *
     * @param name    the name
     * @param fst     the fst
     * @param snd     the snd
     * @param thd     the thd
     * @param closure the closure
     */
    public void createTask(String name, Integer fst, Integer snd,
                           Integer thd, Closure<?> closure) {
        String branchName = branchPattern
                .replace("$1", fst.toString())
                .replace("$2", snd.toString())
                .replace("$3", thd.toString());
        String folderName = folderPattern
                .replace("$1", fst.toString())
                .replace("$2", snd.toString())
                .replace("$3", thd.toString());
        Task tsk = task(name, folderName, branchName, closure);
        tsk.numbers = List.of(fst, snd, thd);
    }

    /**
     * Task task.
     *
     * @param name   the name
     * @param folder the folder
     * @param branch the branch
     * @return the task
     */
    public Task task(String name, String folder, String branch) {
        Task task = new Task(name, folder, branch, taskScore);
        tasks.add(task);
        return task;
    }

    /**
     * Task task.
     *
     * @param name    the name
     * @param folder  the folder
     * @param branch  the branch
     * @param closure the closure
     * @return the task
     */
    public Task task(String name, String folder,
                     String branch, Closure<?> closure) {
        Task task = new Task(name, folder, branch, taskScore);
        Delegator.groovyDelegate(task, closure);
        tasks.add(task);
        return task;
    }
}