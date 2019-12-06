package ru.makar.icss.lab3.report;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.EnvironmentConfiguration;
import org.jtwig.environment.EnvironmentConfigurationBuilder;
import ru.makar.icss.lab3.model.GroupsInfo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static ru.makar.icss.lab3.Constants.PATH_REPORT_TEMPLATE;

public class ReportGenerator {
    private String targetName;
    private GroupsInfo groupsInfo;
    private long totalTime;
    private JtwigTemplate template;

    public ReportGenerator() {
        EnvironmentConfiguration configuration = EnvironmentConfigurationBuilder
                .configuration()
                .resources()
                .withDefaultInputCharset(StandardCharsets.UTF_8)
                .and()
                .build();
        template = JtwigTemplate.classpathTemplate(PATH_REPORT_TEMPLATE, configuration);
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public void setGroupsInfo(GroupsInfo groupsInfo) {
        this.groupsInfo = groupsInfo;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
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
                .with("groups", groupsInfo.getGroups())
                .with("students", groupsInfo.getStudents())
                .with("totalTime", totalTime);
        return template.render(model);
    }
}
