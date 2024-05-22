package nsu.fit.ezaitseva.model.util;

import nsu.fit.ezaitseva.model.entity.fixes.StudentInformation;
import nsu.fit.ezaitseva.model.entity.tasks.Task;

import java.io.*;
import java.util.List;

public class FileManager {
    public static File getTaskFolder(Task task, StudentInformation studentInfo, File workingDir) throws IOException {
        File taskDir = new File(workingDir, task.getFolder());

        String dir = studentInfo.getFolderRename().get(task.id());
        if (dir != null) {
            taskDir = new File(workingDir, dir);
            if (taskDir.exists()) {
                return taskDir;
            }
        }
        List<Integer> numbers = task.getNumbers();
        if (numbers != null && numbers.size() == 3) {
            dir = studentInfo.getFolderPattern()
                    .replace("$1", numbers.get(0).toString())
                    .replace("$2", numbers.get(1).toString())
                    .replace("$3", numbers.get(2).toString());
            taskDir = new File(workingDir, dir);
            if (taskDir.exists()) {
                return taskDir;
            }
        }
        if (taskDir.exists()) {
            return taskDir;
        }
        throw new IOException("There is no folder for " + studentInfo.getStudentConfig().getGitName() + ":" + taskDir);
    }

    public static void styleFileCreate(File taskDir, File checkStyleFile) throws IOException {
        File checkstyleDir = new File(taskDir.getParentFile(), "config/checkstyle");
        checkstyleDir.mkdirs();
        File newCheckStyleFile = new File(checkstyleDir, "checkstyle.xml");
        newCheckStyleFile.createNewFile();
        FileWriter fileWriter = new FileWriter(newCheckStyleFile);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        char[] buffer = new char[1000];
        BufferedReader template = new BufferedReader(new FileReader(checkStyleFile), buffer.length);
        int readAmount = 0;
        while ((readAmount = template.read(buffer)) > 0) {
            bufferedWriter.write(buffer, 0, readAmount);
        }
        bufferedWriter.flush();
        bufferedWriter.close();

        template.close();
    }

    public static void changeGradle(File taskDir, File checkStyleFile) throws IOException {
        char[] buf = new char[1000000];
        File buildGradle = new File(taskDir, "build.gradle");
        FileReader fileReader = new FileReader(buildGradle);
        String gradleText = String.valueOf(buf, 0, fileReader.read(buf));

        FileWriter fw = new FileWriter(new File(taskDir, "build.gradle"));
        BufferedWriter bw = new BufferedWriter(fw);
        if (!gradleText.contains("id 'checkstyle'")) {
//            gradleText = """
//                    plugins{
//                        id 'checkstyle'
//                    }
//                    """ + gradleText;
            gradleText = gradleText.replace("plugins {", "plugins {\n   id 'checkstyle'\n");
        }
        String forStyleChecker = """
                                
                configurations {
                    checkstyleConfig
                }

                checkstyle {
                    toolVersion '10.9.1'
                    //config = resources.text.fromArchiveEntry(configurations.checkstyleConfig, 'google_checks.xml')
                }
                dependencies {
                    checkstyleConfig('com.puppycrawl.tools:checkstyle:10.9.1') { transitive = false }
                }

                """;
        if (!gradleText.contains(forStyleChecker)) {
            gradleText += forStyleChecker;
        }
        bw.write(gradleText);
        bw.flush();
        fw.flush();
        bw.close();
        fileReader.close();
    }

    public static void addCheckStyle(File taskDir, File checkStyleFile) throws IOException {
        styleFileCreate(taskDir, checkStyleFile);
        changeGradle(taskDir, checkStyleFile);


//        bufferedWriter.close();
//        fileWriter.close();

    }
}