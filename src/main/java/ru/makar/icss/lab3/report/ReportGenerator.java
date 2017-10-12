package ru.makar.icss.lab3.report;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.EnvironmentConfiguration;
import org.jtwig.environment.EnvironmentConfigurationBuilder;
import ru.makar.icss.lab3.model.Group;
import ru.makar.icss.lab3.model.Student;


import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static ru.makar.icss.lab3.Constants.PATH_REPORT_TEMPLATE;

public class ReportGenerator {
    private String targetName;
    private List<Group> groups;
    private List<Student> students;
    private JtwigTemplate template;

    public ReportGenerator() {
        EnvironmentConfiguration configuration = EnvironmentConfigurationBuilder
                .configuration()
                .resources()
                .withDefaultInputCharset(Charset.forName("UTF-8"))
                .and()
                .build();
        template = JtwigTemplate.classpathTemplate(PATH_REPORT_TEMPLATE, configuration);
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public File generate(File destinationPath) {
        if (destinationPath != null && destinationPath.exists() && destinationPath.isDirectory()) {
            File report = new File(destinationPath, isNameEmpty() ? "report.html" : targetName);
            if (report.exists()) {
                if (!report.delete()) {
                    return null;
                }
            }
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(report.toURI()))) {
                writer.write(generateReportContent());
                return report;
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    private boolean isNameEmpty() {
        return targetName == null || targetName.isEmpty();
    }

    private String generateReportContent() {
        JtwigModel model = JtwigModel.newModel()
                                     .with("groups", groups)
                                     .with("students", students);
        return template.render(model);
    }
}
