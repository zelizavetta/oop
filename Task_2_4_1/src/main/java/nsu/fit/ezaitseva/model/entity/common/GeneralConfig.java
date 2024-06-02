package nsu.fit.ezaitseva.model.entity.common;

import groovy.lang.Closure;
import lombok.Data;
import nsu.fit.ezaitseva.model.dsl.Delegator;

/**
 * The type General config.
 */
@Data
public class GeneralConfig {
    /**
     * The Git.
     */
    GitConfig git = new GitConfig();
    /**
     * The Evaluation config.
     */
    EvaluationConfig evaluationConfig = new EvaluationConfig();

    /**
     * Git.
     *
     * @param closure the closure
     */
    public void git(Closure<?> closure) {
        Delegator.groovyDelegate(git, closure);
    }

    /**
     * Evaluation.
     *
     * @param closure the closure
     */
    public void evaluation(Closure<?> closure) {
        Delegator.groovyDelegate(evaluationConfig, closure);
    }

}