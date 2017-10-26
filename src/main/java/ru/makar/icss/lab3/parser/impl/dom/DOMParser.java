package ru.makar.icss.lab3.parser.impl.dom;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import ru.makar.icss.lab3.model.GroupsInfo;
import ru.makar.icss.lab3.parser.XmlParser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public final class DOMParser implements XmlParser {
    private DOMDataExtractor dataExtractor;

    public DOMParser() {
        dataExtractor = new DOMDataExtractor();
    }

    @Override
    public GroupsInfo parse(File xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(xml);
            return dataExtractor.extractInfo(document);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
