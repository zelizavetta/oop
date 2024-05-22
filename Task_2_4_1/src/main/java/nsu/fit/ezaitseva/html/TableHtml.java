package nsu.fit.ezaitseva.html;

import nsu.fit.ezaitseva.model.entity.attendance.Lesson;
import nsu.fit.ezaitseva.model.entity.tasks.Task;
import nsu.fit.ezaitseva.model.evaluator.Assessment;

import javax.swing.text.html.HTML;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TableHtml {
    private HTML html;

    public TableHtml() {
        LinkedList<?> linkedList;
    }

    public void smth(OutputStream outputStream, Map<String, Map<Lesson, Boolean>> attendance,
                     Map<String, Map<String, Assessment>> studentsAssessmentMap,
                     List<Task> tasks, File generalDir) throws IOException {
        System.out.println(attendance);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        outputStreamWriter.write("""
                <!DOCTYPE html>
                <html>
                <style>
                table {
                    border-collapse: collapse;
                }

                td {
                    border: 1px solid black;
                    line-height: 1.5em;
                    font-size: 14px;
                    font-family: monospace;
                    text-align: center;
                    font-weight: normal;
                }

                h1 {
                    font-family: monospace;
                    font-size: 24px;
                    font-weight: normal;
                }
                </style>

                <head>
                <title>%s</title>
                </head>
                <body>
                                <h1>%s</h1>
                <table style="width:90%%">
                                
                """.formatted("group22214", "group22214"));
        String rowTask = getCell("names");
        for (Task task : tasks) {
            rowTask += getCell(task.id());
        }
        addRow(outputStreamWriter, rowTask);
        Map<String, String> studentsRows = new HashMap<>();
        List<String> studentList = studentsAssessmentMap.keySet().stream().toList();
        studentList.forEach((gitName) -> {
            studentsRows.put(gitName, getCell(gitName));
        });

        for (Task task : tasks) {
            for (String gitName : studentList) {
                String prev = studentsRows.get(gitName);
                Assessment assessmentForTask = studentsAssessmentMap.get(gitName).get(task.id());
                String newRow = prev
                        + getCell(assessmentForTask != null ? assessmentForTask.formalize() : new Assessment());
                studentsRows.put(gitName, newRow);
            }
        }
        for (String gitName : studentList) {
            addRow(outputStreamWriter, studentsRows.get(gitName));
        }

        outputStreamWriter.write("""
                </table>
                """);
        outputStreamWriter.write("""
                                
                                <h1>%s</h1>
                <table style="width:90%%">
                <tr>
                        <td>name</td>
                        <td>score</td>
                        <td>mark</td>
                        <td>moreInfo</td>
                </tr>
                """.formatted("group22214"));

        studentsAssessmentMap.forEach((gitName, mapAss) -> {
            double val = mapAss.values().stream().map((Assessment::getSummary)).reduce(.0, Double::sum);
            int mark = val >= 6 ? 5 : val >= 4 ? 4 : val >= 2 ? 3 : 2; //TODO: score to mark
            String row = getCell(gitName);
            row += getCell("%.2f".formatted(val));
            row += getCell(mark);
            row += getCell("<a href=\"%s_info.html\"> more</a>".formatted(gitName));
            try {
                addRow(outputStreamWriter, row);
            } catch (IOException e) {
                System.err.println(e);
            }
            try {
                moreInfo(gitName, tasks, generalDir);
            } catch (IOException e) {
                System.err.println(e);
            }

        });


        outputStreamWriter.write("""
                </table>
                """);
        outputStreamWriter.write("""
                </body>
                </html>
                """);
        outputStreamWriter.flush();
        outputStreamWriter.close();
    }

    private void moreInfo(String gitName, List<Task> tasks, File studentsDir) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                new FileOutputStream("htmls/" + gitName + "_info.html"));
        outputStreamWriter.write("""
                <!DOCTYPE html>
                <html>
                <style>
                table {
                    border-collapse: collapse;
                }

                td {
                    border: 1px solid black;
                    line-height: 1.5em;
                    font-size: 14px;
                    font-family: monospace;
                    text-align: center;
                    font-weight: normal;
                }

                h1 {
                    font-family: monospace;
                    font-size: 24px;
                    font-weight: normal;
                }
                </style>

                <head>
                <title>%s</title>
                </head>
                <body>
                                <h1>%s</h1>
                <table style="width:90%%">
                <tr>
                        <td>task</td>
                        <td>jacoco</td>
                        <td>tests</td>
                        <td>checkstyle</td>
                </tr>
                                
                """.formatted(gitName, "group22214"));
        File studentDir = new File(studentsDir, gitName);
        for (Task task : tasks) {
            String row = getCell(task.id());
            File taskDir = new File(studentDir, task.id());
            if (!taskDir.exists()) {
                continue;
            }
            row += getCell(ref("click", new File(taskDir, "build/reports/jacoco/test/html/index.html")));
            row += getCell(ref("click", new File(taskDir, "build/reports/tests/test/index.html")));
            row += getCell(ref("click", new File(taskDir, "build/reports/checkstyle/main.html")));
            addRow(outputStreamWriter, row);
        }
        outputStreamWriter.write("""
                </table>
                """);
        outputStreamWriter.write("""
                </body>
                </html>
                """);
        outputStreamWriter.flush();
        outputStreamWriter.close();

    }

//    private String ref(String name, String filePath) {
//        return ref(name, new File(filePath));
//    }

    private String ref(String name, File file) {
        if (file.exists()) {
//            File htmls = new File("./htmls");
//            String fileName = gitName + "_" + file.getName();
//            File saveFile = new File(htmls, fileName);

            return "<a href=\"..\\%s\">%s</a>".formatted(file.getPath(), name);
        } else {
            return "<a>-</a>";
        }
    }

    private void addRow(OutputStreamWriter writer, String str) throws IOException {
        writer.write("<tr>\n");
        writer.write(str);
        writer.write("</tr>\n");
    }

    private String getCell(String str) {
        return "<td>" + str + "</td>\n";
    }

    private String getCell(Object... objects) {
        StringBuilder answer = new StringBuilder();
        for (Object object : objects) {
            answer.append("<td>").append(object.toString()).append("</td>\n");
        }
        return answer.toString();
    }


}