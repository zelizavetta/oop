package nsu.fit.ezaitseva.model.entity.common;

import lombok.Data;

/**
 * The type Git config.
 */
@Data
public class GitConfig {
    /**
     * The Repo link prefix.
     */
    String repoLinkPrefix = "https://github.com/";
    /**
     * The Repo link postfix.
     */
    String repoLinkPostfix = ".git";
    /**
     * The Default repository.
     */
    String defaultRepository;
    /**
     * The Docs branch.
     */
    String docsBranch = "gh-pages";
    /**
     * The Default branch.
     */
    String defaultBranch = "main";
}