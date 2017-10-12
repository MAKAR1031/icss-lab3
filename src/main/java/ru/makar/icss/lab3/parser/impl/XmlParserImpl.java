package ru.makar.icss.lab3.parser.impl;

import ru.makar.icss.lab3.model.Group;
import ru.makar.icss.lab3.model.Student;
import ru.makar.icss.lab3.parser.DataExtractor;
import ru.makar.icss.lab3.parser.XmlParser;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;

public class XmlParserImpl implements XmlParser {

    private final File xml;
    private DataExtractor dataExtractor;

    public static XmlParser parse(File xml) {
        return new XmlParserImpl(xml);
    }

    @Override
    public boolean extractData(List<Group> groups, List<Student> students) {
        if (groups == null || students == null) {
            return false;
        }
        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(xml))) {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(stream);
            int event = reader.getEventType();
            while (true) {
                if (event == START_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if ("groups".equals(tagName)) {
                        dataExtractor.extractGroups(reader, groups);
                    }
                    if ("students".equals(tagName)) {
                        dataExtractor.extractStudents(reader, students);
                    }
                }
                if (!reader.hasNext())
                    break;
                event = reader.next();
            }
            reader.close();
            groups.forEach(g -> {
                BigDecimal baseCost = g.getBaseCost();
                long studentsCount = students.stream()
                                             .filter(s -> s.getGroupRef() == g.getId() && s.isOnBudget())
                                             .count();
                g.setEducationCost(baseCost.multiply(BigDecimal.valueOf(studentsCount < 2 ? 2 : studentsCount)));
            });
            return true;
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
        return false;
    }

    private XmlParserImpl(File file) {
        this.xml = file;
        dataExtractor = new DataExtractorImpl();
    }
}
