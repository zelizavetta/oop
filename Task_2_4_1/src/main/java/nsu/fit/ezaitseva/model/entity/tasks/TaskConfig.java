package nsu.fit.ezaitseva.model.entity.tasks;

import groovy.lang.Closure;
import lombok.Data;
import nsu.fit.ezaitseva.model.dsl.Delegator;
import nsu.fit.ezaitseva.model.entity.common.GeneralConfig;

import java.util.ArrayList;
import java.util.List;

@Data
public class TaskConfig {
    List<Task> tasks = new ArrayList<>();
    Double taskScore;
    private String branchPattern = "task-$1-$2-$3";
    private String folderPattern = "Task_$1_$2_$3";

    public TaskConfig(GeneralConfig generalConfig) {
        taskScore = generalConfig.getEvaluationConfig().getTaskScore();
    }

    public void tasks(Closure<?> closure) {
        Delegator.groovyDelegate(this, closure);
    }

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

    public Task task(String name, String folder, String branch) {
        Task task = new Task(name, folder, branch, taskScore);
        tasks.add(task);
        return task;
    }

    public Task task(String name, String folder,
                     String branch, Closure<?> closure) {
        Task task = new Task(name, folder, branch, taskScore);
        Delegator.groovyDelegate(task, closure);
        tasks.add(task);
        return task;
    }
}