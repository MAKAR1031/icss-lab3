package ru.makar.icss.lab3.parser;

import ru.makar.icss.lab3.model.Group;
import ru.makar.icss.lab3.model.Student;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.List;

public interface DataExtractor {
    void extractGroups(XMLStreamReader reader, List<Group> groups) throws XMLStreamException;
    void extractStudents(XMLStreamReader reader, List<Student> students) throws XMLStreamException;
}
