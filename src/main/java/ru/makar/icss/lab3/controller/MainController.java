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
import ru.makar.icss.lab3.model.GroupsInfo;
import ru.makar.icss.lab3.parser.XmlParser;
import ru.makar.icss.lab3.parser.impl.XmlValidator;
import ru.makar.icss.lab3.parser.impl.stax.StAXParser;
import ru.makar.icss.lab3.report.ReportGenerator;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
    private XmlParser xmlParser;
    private ReportGenerator reportGenerator;
    private BooleanProperty stateClear;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Сохранить отчет");
        xmlValidator = new XmlValidator();
        xmlParser = new StAXParser();
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
                if (xsd == null || !xsd.exists()) {
                    dropPane.getStyleClass().add("generate-error");
                    dropPaneLabel.setText(Constants.MESSAGE_XSD_NOT_FOUND);
                    return;
                }
                if (xml == null || !xml.exists()) {
                    dropPane.getStyleClass().add("generate-error");
                    dropPaneLabel.setText(Constants.MESSAGE_XML_NOT_FOUND);
                    return;
                }
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
        boolean xmlValid = xmlValidator.validate(xsd, xml);
        if (!xmlValid) {
            dropPaneLabel.setText(MESSAGE_XML_INVALID);
            return false;
        }
        GroupsInfo info = xmlParser.parse(xml);
        if (info == null) {
            dropPaneLabel.setText(MESSAGE_ERROR);
            return false;
        }
        String targetName = xml.getName().replaceAll("\\.xml", "") + "-report.html";
        reportGenerator.setTargetName(targetName);
        reportGenerator.setGroupsInfo(info);
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
