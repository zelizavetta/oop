package nsu.fit.ezaitseva.model.entity.common;

import lombok.Data;

@Data
public class EvaluationConfig {
    Double taskScore = 1.0;
    Double softDeadLinePenalty = 0.5;
    Double hardDeadLinePenalty = 0.5;
    Double jacocoPercentage = 80.0;
    Double jacocoScore = 1.0;
    Double checkStylePercentage = 0.2;
    Double checkStyleScore = 1.0;
    Double buildScore;
    Double docScore;
}