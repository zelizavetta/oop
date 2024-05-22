package nsu.fit.ezaitseva.model.git;

import nsu.fit.ezaitseva.model.entity.common.GitConfig;
import nsu.fit.ezaitseva.model.entity.fixes.StudentInformation;
import nsu.fit.ezaitseva.model.entity.group.StudentConfig;
import nsu.fit.ezaitseva.model.entity.tasks.Task;
import nsu.fit.ezaitseva.model.util.FileManager;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PersonGit implements AutoCloseable {
    Git git;
    StudentInformation studentInfo;
    StudentConfig studentConfig;
    GitConfig gitConfig;
    File workingDir;

    public String gitName() {
        return studentConfig.getGitName();
    }
    public StudentConfig studentConfig(){
        return studentConfig;
    }
    public StudentInformation studentInfo(){
        return studentInfo;
    }

    public PersonGit(GitConfig gitConfig, StudentInformation studentInfo,
                     File workingDir) throws IOException, GitAPIException {
        this.gitConfig = gitConfig;
        this.studentInfo = studentInfo;
        this.studentConfig = studentInfo.getStudentConfig();
        this.workingDir = workingDir;
        initFolder();
//        git.log().call().forEach((revCommit -> {
//            System.out.println(revCommit.getCommitTime() + "|" + revCommit.getCommitterIdent().getTimeZone() + "|"
//                    + revCommit.getCommitterIdent().getWhen() + "|" + revCommit);
//        }));
//        try (DiffFormatter diffFormatter = new DiffFormatter(DisabledOutputStream.INSTANCE)) {
//            diffFormatter.setRepository(git.getRepository());
//            List<DiffEntry> diffEntries = diffFormatter.scan(git.log().call().iterator().next(),
//                    git.log().call().iterator().next());
//            FileHeader fileHeader = diffFormatter.toFileHeader(diffEntries.get(0));
//            System.out.println(fileHeader.toEditList());
//        }
    }

    public File switchTaskIfNotExists(Task task) throws GitAPIException, IOException {
        try {
            return tryFile(task);
        } catch (IOException e) {
            switchTaskBranch(task);
            return tryFile(task);
        }
    }

    private File tryFile(Task task) throws IOException {
        return FileManager.getTaskFolder(task, studentInfo, workingDir);
    }


    public void switchTaskBranch(Task task) throws GitAPIException {
        String taskBranch = task.getBranch();
        String branchByAlias = studentInfo.getBranchRename().get(task.id());
        RefNotFoundException refNotFoundException = null;
        if (branchByAlias != null) {
            try {
                switchBranch("origin/" + branchByAlias);
                return;
            } catch (RefNotFoundException e) {
                refNotFoundException = e;
                System.err.println("Wrong branch alias for " + studentConfig.getGitName() + ": " + branchByAlias);
            }
        }

        List<Integer> numbers;
        if ((numbers = task.getNumbers()) != null && numbers.size() == 3) {
            String branchByPattern = studentInfo.getBranchPattern().replace("$1", numbers.get(0).toString())
                    .replace("$2", numbers.get(1).toString())
                    .replace("$3", numbers.get(2).toString());
            try {
                switchBranch("origin/" + branchByPattern);
                return;
            } catch (RefNotFoundException e) {
                System.err.println("Wrong branch pattern for " + studentConfig.getGitName() + ": "
                        + studentInfo.getBranchPattern());
                refNotFoundException = e;
            }
        }
        try {
            switchBranch("origin/" + taskBranch);
            return;
        } catch (RefNotFoundException e) {
            System.err.println("Wrong branch by default taskBranch for " + studentConfig.getGitName() + ": "
                    + taskBranch);
            refNotFoundException = e;
        }

        throw refNotFoundException;
    }

    private void switchBranch(String branchName) throws GitAPIException {
        git.checkout()
                .setName(branchName)
                .setForced(true)
                .call();
    }


    private void initFolder() throws IOException, GitAPIException {
        System.out.println(workingDir);
        System.out.println(studentConfig.getRepository());
        if (workingDir.exists()) {
            git = Git.open(workingDir);

            try {

                switchBranch("origin/" + studentConfig.getDefaultBranch());
            } catch (RefAlreadyExistsException e) {
                System.err.println("WHY?");
                switchBranch(studentConfig.getDefaultBranch());
            }
        } else {
            String URI = gitConfig.getRepoLinkPrefix() + studentConfig.getGitName()
                    + "/" + studentConfig.getRepository();
            git = Git.cloneRepository()
                    .setDirectory(workingDir)
                    .setURI(URI)
                    .setCloneAllBranches(true)
                    .call();
        }

    }

    public Iterable<RevCommit> getCommits() throws GitAPIException {
        return git.log().call();
    }

    @Override
    public void close() throws Exception {

        git.close();
    }
}