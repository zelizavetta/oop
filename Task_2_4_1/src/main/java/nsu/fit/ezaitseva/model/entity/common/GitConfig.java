package nsu.fit.ezaitseva.model.entity.common;

import lombok.Data;

@Data
public class GitConfig {
    String repoLinkPrefix = "https://github.com/";
    String repoLinkPostfix = ".git";
    String defaultRepository;
    String docsBranch = "gh-pages";
    String defaultBranch = "main";
}