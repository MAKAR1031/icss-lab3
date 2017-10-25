package ru.makar.icss.lab3.parser.impl.stax;

import ru.makar.icss.lab3.model.GroupsInfo;
import ru.makar.icss.lab3.parser.XmlParser;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public final class StAXParser implements XmlParser {
    private StAXDataExtractor dataExtractor;

    public StAXParser() {
        dataExtractor = new StAXDataExtractor();
    }

    @Override
    public GroupsInfo parse(File xml) {
        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(xml))) {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(stream);
            GroupsInfo info = dataExtractor.extractInfo(reader);
            reader.close();
            return info;
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
        return null;
    }
}
