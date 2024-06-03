package nsu.fit.ezaitseva.model.entity.common;

import lombok.Data;

/**
 * The type Evaluation config.
 */
@Data
public class EvaluationConfig {
    /**
     * The Task score.
     */
    Double taskScore = 1.0;
    /**
     * The Soft dead line penalty.
     */
    Double softDeadLinePenalty = 0.5;
    /**
     * The Hard dead line penalty.
     */
    Double hardDeadLinePenalty = 0.5;
    /**
     * The Jacoco percentage.
     */
    Double jacocoPercentage = 80.0;
    /**
     * The Jacoco score.
     */
    Double jacocoScore = 1.0;
    /**
     * The Check style percentage.
     */
    Double checkStylePercentage = 0.2;
    /**
     * The Check style score.
     */
    Double checkStyleScore = 1.0;
    /**
     * The Build score.
     */
    Double buildScore;
    /**
     * The Doc score.
     */
    Double docScore;
}