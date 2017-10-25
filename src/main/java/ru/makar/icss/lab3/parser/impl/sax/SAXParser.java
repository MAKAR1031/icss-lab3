package ru.makar.icss.lab3.parser.impl.sax;

import org.xml.sax.SAXException;
import ru.makar.icss.lab3.model.GroupsInfo;
import ru.makar.icss.lab3.parser.XmlParser;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class SAXParser implements XmlParser {
    @Override
    public GroupsInfo parse(File xml) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            javax.xml.parsers.SAXParser parser = factory.newSAXParser();
            GroupsInfoHandler handler = new GroupsInfoHandler();
            parser.parse(xml, handler);
            return handler.getInfo();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }
}
