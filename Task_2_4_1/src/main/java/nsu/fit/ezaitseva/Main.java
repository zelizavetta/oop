package nsu.fit.ezaitseva;

import nsu.fit.ezaitseva.html.TableHtml;
import nsu.fit.ezaitseva.model.GroovyModel;
import nsu.fit.ezaitseva.model.entity.attendance.AttendanceChecker;
import nsu.fit.ezaitseva.model.evaluator.Assessment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        File generalDir = new File("newFolder");
        File scriptsDir = new File("Task_2_4_1/scripts");
        GroovyModel groovyModel = new GroovyModel(scriptsDir, generalDir);
        TableHtml tableHtml = new TableHtml();
        File htmlDir = new File("htmls");
        htmlDir.mkdir();
        File htmlFile = new File(htmlDir, "table.html");
        try {
            htmlFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<String, Map<String, Assessment>> evaluationResults =
                groovyModel.evaluatePersons(List.of("zelizavetta", "a"));
        try {
            tableHtml.smth(new FileOutputStream(htmlFile),
                    AttendanceChecker.checkAttendance(groovyModel.generalConfig.getGit(),
                            groovyModel.studentInformationMap.values(), generalDir,
                            groovyModel.lessonsConfig.getLessonList())
                    ,
                    evaluationResults
                    ,
                    groovyModel.taskConfig.getTasks(), generalDir);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}