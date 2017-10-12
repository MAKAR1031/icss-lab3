package ru.makar.icss.lab3.parser;

import ru.makar.icss.lab3.model.Group;
import ru.makar.icss.lab3.model.Student;

import java.util.List;

public interface XmlParser {
    boolean extractData(List<Group> groups, List<Student> students);
}
