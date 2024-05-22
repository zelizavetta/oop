package nsu.fit.ezaitseva.model.evaluator;

import lombok.Builder;

import java.util.Objects;

@Builder
public class Assessment {
    @Builder.Default
    private Double buildMark = .0;
    @Builder.Default
    private Double docsMark = .0;
    @Builder.Default
    private Double testMark = .0;
    @Builder.Default
    private Double extraScores = .0;
    @Builder.Default
    private Double styleScores = .0;

    public Assessment() {

    }

    public Assessment(Double buildMark, Double docsMark,
                      Double testMark, Double extraScores, Double styleScores) {
        this.buildMark = buildMark;
        this.docsMark = docsMark;
        this.testMark = testMark;
        this.extraScores = extraScores;
        this.styleScores = styleScores;
    }

    public double getSummary() {
        if (buildMark == 0) {
            return 0;
        }

        return buildMark + docsMark + testMark + extraScores + styleScores;

    }

    public Double compileMark() {
        return buildMark;
    }

    public Double docsMark() {
        return docsMark;
    }

    public Double testMark() {
        return testMark;
    }

    public Double extraScores() {
        return extraScores;
    }

    public Double styleScores() {
        return styleScores;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Assessment) obj;
        return Objects.equals(this.buildMark, that.buildMark) &&
                Objects.equals(this.docsMark, that.docsMark) &&
                Objects.equals(this.testMark, that.testMark) &&
                Objects.equals(this.styleScores, that.styleScores) &&
                Objects.equals(this.extraScores, that.extraScores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildMark, docsMark, testMark, extraScores, styleScores);
    }

    @Override
    public String toString() {
        return buildMark + " " + docsMark + " " + testMark + " " + styleScores + " " + extraScores + " " + getSummary();
    }

    public String formalize() {
        return notZero(buildMark, "b")
                + " " + notZero(docsMark, "d")
                + " " + notZero(testMark, "t")
                + " " + notZero(styleScores, "s")
                + " " + notZero(extraScores, "e")
                + " " + "%.2f".formatted(getSummary());
    }

    public static String toFormat(Assessment assessment) {
        return assessment.formalize();
    }

    private String notZero(Double d, String common) {
        return d != .0 ? common : "-";
    }


}