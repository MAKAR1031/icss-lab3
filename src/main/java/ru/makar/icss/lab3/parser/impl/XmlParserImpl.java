package ru.makar.icss.lab3.parser.impl;

import ru.makar.icss.lab3.model.GroupsInfo;
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

public class XmlParserImpl implements XmlParser {

    private final File xml;
    private DataExtractor dataExtractor;

    public static XmlParser parse(File xml) {
        return new XmlParserImpl(xml);
    }

    @Override
    public GroupsInfo extractData() {
        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(xml))) {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(stream);
            GroupsInfo info = dataExtractor.extractInfo(reader);
            reader.close();
            info.getGroups().forEach(g -> {
                BigDecimal baseCost = g.getBaseCost();
                long studentsCount = info.getStudents().stream()
                                         .filter(s -> s.getGroupRef() == g.getId() && s.isOnBudget())
                                         .count();
                g.setEducationCost(baseCost.multiply(BigDecimal.valueOf(studentsCount < 2 ? 2 : studentsCount)));
            });
            return info;
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
        return null;
    }

    private XmlParserImpl(File file) {
        this.xml = file;
        dataExtractor = new DataExtractorImpl();
    }
}
