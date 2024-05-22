evaluation {
    taskScore = 1.0
    softDeadLinePenalty = -0.5
    hardDeadLinePenalty = -0.5

    jacocoScore = 0.4
    checkStyleScore = 0.2
    buildScore = 0.2
    docScore = 0.2

    jacocoPercentage = 80
    checkStylePercentage = 1
}

git {
    repoLinkPrefix = 'https://github.com/'
    repoLinkPostfix = '.git'
    defaultRepository = 'oop'
    docsBranch = 'gh-pages'
    defaultBranch = 'main'
}