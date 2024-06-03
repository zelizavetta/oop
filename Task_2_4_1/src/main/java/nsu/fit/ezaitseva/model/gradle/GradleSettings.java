package nsu.fit.ezaitseva.model.gradle;

import lombok.Builder;

/**
 * The type Gradle settings.
 */
@Builder
public class GradleSettings {
    @Builder.Default
    private boolean runTest = true;
    @Builder.Default
    private boolean runDocs = true;
    @Builder.Default
    private boolean runCheckstyle = true;
}