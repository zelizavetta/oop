package nsu.fit.ezaitseva.model.evaluator;

import java.util.Objects;
import lombok.Builder;

/**
 * The type Assessment.
 */
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

    /**
     * Instantiates a new Assessment.
     */
    public Assessment() {

    }

    /**
     * Instantiates a new Assessment.
     *
     * @param buildMark   the build mark
     * @param docsMark    the docs mark
     * @param testMark    the test mark
     * @param extraScores the extra scores
     * @param styleScores the style scores
     */
    public Assessment(Double buildMark, Double docsMark,
                      Double testMark, Double extraScores, Double styleScores) {
        this.buildMark = buildMark;
        this.docsMark = docsMark;
        this.testMark = testMark;
        this.extraScores = extraScores;
        this.styleScores = styleScores;
    }

    /**
     * Gets summary.
     *
     * @return the summary
     */
    public double getSummary() {
        if (buildMark == 0) {
            return 0;
        }

        return buildMark + docsMark + testMark + extraScores + styleScores;

    }

    /**
     * Compile mark double.
     *
     * @return the double
     */
    public Double compileMark() {
        return buildMark;
    }

    /**
     * Docs mark double.
     *
     * @return the double
     */
    public Double docsMark() {
        return docsMark;
    }

    /**
     * Test mark double.
     *
     * @return the double
     */
    public Double testMark() {
        return testMark;
    }

    /**
     * Extra scores double.
     *
     * @return the double
     */
    public Double extraScores() {
        return extraScores;
    }

    /**
     * Style scores double.
     *
     * @return the double
     */
    public Double styleScores() {
        return styleScores;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (Assessment) obj;
        return Objects.equals(this.buildMark, that.buildMark)
                && Objects.equals(this.docsMark, that.docsMark)
                && Objects.equals(this.testMark, that.testMark)
                && Objects.equals(this.styleScores, that.styleScores)
                && Objects.equals(this.extraScores, that.extraScores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildMark, docsMark, testMark, extraScores, styleScores);
    }

    @Override
    public String toString() {
        return buildMark + " " + docsMark + " " + testMark + " "
                + styleScores + " " + extraScores + " " + getSummary();
    }

    /**
     * Formalize string.
     *
     * @return the string
     */
    public String formalize() {
        return notZero(buildMark, "b")
                + " " + notZero(docsMark, "d")
                + " " + notZero(testMark, "t")
                + " " + notZero(styleScores, "s")
                + " " + notZero(extraScores, "e")
                + " " + "%.2f".formatted(getSummary());
    }

    /**
     * To format string.
     *
     * @param assessment the assessment
     * @return the string
     */
    public static String toFormat(Assessment assessment) {
        return assessment.formalize();
    }

    private String notZero(Double d, String common) {
        return d != .0 ? common : "-";
    }


}