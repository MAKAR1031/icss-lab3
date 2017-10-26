package ru.makar.icss.lab3;

public interface Constants {
    String PATH_MAIN_LAYOUT = "fxml/mainStage.fxml";
    String PATH_APP_STYLE = "css/app.css";
    String PATH_REPORT_TEMPLATE = "template/report-template.twig";

    String DEFAULT_SCHEMA_NAME = "schema.xsd";

    String MESSAGE_DROP = "Перетащи сюда xml и xsd";
    String MESSAGE_RELEASE = "А теперь отпусти";
    String MESSAGE_XSD_NOT_FOUND = "XSD схема не найдена";
    String MESSAGE_XML_NOT_FOUND = "XML документ не найден";
    String MESSAGE_REPORT_GENERATE = "Генерация отчета";
    String MESSAGE_COMPLETE = "Отчет готов";
    String MESSAGE_XML_INVALID = "XML документ не прошел проверку:\n";
    String MESSAGE_ERROR = "Во время работы произошла ошибка :(";
}
