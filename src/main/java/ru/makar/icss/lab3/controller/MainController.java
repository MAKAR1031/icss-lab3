package ru.makar.icss.lab3.controller;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import ru.makar.icss.lab3.Constants;
import ru.makar.icss.lab3.model.Group;
import ru.makar.icss.lab3.model.Student;
import ru.makar.icss.lab3.parser.XmlValidator;
import ru.makar.icss.lab3.parser.impl.XmlParserImpl;
import ru.makar.icss.lab3.parser.impl.XmlValidatorImpl;
import ru.makar.icss.lab3.report.ReportGenerator;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static ru.makar.icss.lab3.Constants.*;

public class MainController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Pane dropPane;

    @FXML
    private Label dropPaneLabel;

    @FXML
    private Button resetButton;

    private DirectoryChooser directoryChooser;
    private XmlValidator xmlValidator;
    private ReportGenerator reportGenerator;
    private BooleanProperty stateClear;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Сохранить отчет");
        xmlValidator = new XmlValidatorImpl();
        reportGenerator = new ReportGenerator();
        dropPaneLabel.setText(MESSAGE_DROP);
        stateClear = new SimpleBooleanProperty();
        stateClear.set(true);
        resetButton.visibleProperty().bind(stateClear.not());
    }

    @FXML
    private void onDragOver(DragEvent event) {
        dropPane.getStyleClass().add("active");
        dropPaneLabel.getStyleClass().add("active");
        if (stateClear.get()) {
            dropPaneLabel.setText(MESSAGE_RELEASE);
        }
        event.acceptTransferModes(TransferMode.MOVE);
    }

    @FXML
    private void onDragExited() {
        if (stateClear.get()) {
            dropPaneLabel.setText(MESSAGE_DROP);
        }
        dropPane.getStyleClass().removeAll("active");
        dropPaneLabel.getStyleClass().removeAll("active");
    }

    @FXML
    private void onDrop(DragEvent event) {
        if (!stateClear.get()) {
            return;
        }
        stateClear.set(false);
        dropPaneLabel.setText(MESSAGE_REPORT_GENERATE);
        Dragboard dragboard = event.getDragboard();
        List<File> files = dragboard.getFiles();
        event.setDropCompleted(true);
        event.consume();
        Platform.runLater(() -> {
            if (files != null) {
                File xsd = getXSDSchema(files);
                File xml = getXMLDocument(files);
                if (generateReport(xsd, xml)) {
                    dropPane.getStyleClass().add("generate-complete");
                    dropPaneLabel.setText(Constants.MESSAGE_COMPLETE);
                } else {
                    dropPane.getStyleClass().add("generate-error");
                }
            }
        });
    }

    @FXML
    public void reset() {
        stateClear.set(true);
        dropPane.getStyleClass().removeAll("generate-complete", "generate-error");
        onDragExited();
    }

    private File getXSDSchema(List<File> files) {
        return files.stream().filter(f -> f.getName().endsWith(".xsd")).findAny().orElse(getDefaultXSDSchema());
    }

    private File getDefaultXSDSchema() {
        return new File(System.getProperty("user.dir") + File.separator + Constants.DEFAULT_SCHEMA_NAME);
    }

    private File getXMLDocument(List<File> files) {
        return files.stream().filter(f -> f.getName().endsWith(".xml")).findAny().orElse(null);
    }

    private boolean generateReport(File xsd, File xml) {
        List<Group> groups = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        boolean xmlValid = xmlValidator.validate(xsd, xml);
        if (!xmlValid) {
            dropPaneLabel.setText(MESSAGE_XML_INVALID);
            return false;
        }
        boolean dataExtracted = XmlParserImpl.parse(xml).extractData(groups, students);
        if (!dataExtracted) {
            dropPaneLabel.setText(MESSAGE_ERROR);
            return false;
        }
        String targetName = xml.getName().replaceAll("\\.xml", "") + "-report.html";
        reportGenerator.setTargetName(targetName);
        reportGenerator.setGroups(groups);
        reportGenerator.setStudents(students);
        File destination = directoryChooser.showDialog(rootPane.getScene().getWindow());
        File report = reportGenerator.generate(destination);
        if (report != null) {
            try {
                Desktop.getDesktop().open(report);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return report != null;
    }
}
