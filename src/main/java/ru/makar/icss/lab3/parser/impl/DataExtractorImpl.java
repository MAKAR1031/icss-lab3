package ru.makar.icss.lab3.parser.impl;

import ru.makar.icss.lab3.model.Group;
import ru.makar.icss.lab3.model.GroupsInfo;
import ru.makar.icss.lab3.model.Student;
import ru.makar.icss.lab3.parser.DataExtractor;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;

public class DataExtractorImpl implements DataExtractor {

    @Override
    public GroupsInfo extractInfo(XMLStreamReader reader) throws XMLStreamException {
        GroupsInfo info = new GroupsInfo();
        int event = reader.getEventType();
        while (true) {
            if (event == START_ELEMENT && "groups".equals(reader.getLocalName())) {
                info.getGroups().addAll(extractGroups(reader));
                event = reader.getEventType();
            }
            if (event == START_ELEMENT && "students".equals(reader.getLocalName())) {
                info.getStudents().addAll(extractStudents(reader));
            }

            if (!reader.hasNext()) {
                break;
            }
            event = reader.next();
        }
        return info;
    }

    private List<Group> extractGroups(XMLStreamReader reader) throws XMLStreamException {
        List<Group> groups = new ArrayList<>();
        int event = reader.getEventType();
        while (true) {
            if (event == START_ELEMENT && "group".equals(reader.getLocalName())) {
                groups.add(extractGroup(reader));
                event = reader.getEventType();
            }
            if (event == END_ELEMENT && "groups".equals(reader.getLocalName())) {
                break;
            }

            if (!reader.hasNext()) {
                break;
            }
            event = reader.next();
        }
        return groups;
    }

    private List<Student> extractStudents(XMLStreamReader reader) throws XMLStreamException {
        List<Student> students = new ArrayList<>();
        int event = reader.getEventType();
        while (true) {
            if (event == START_ELEMENT && "student".equals(reader.getLocalName())) {
                students.add(extractStudent(reader));
                event = reader.getEventType();
            }
            if (event == END_ELEMENT && "students".equals(reader.getLocalName())) {
                break;
            }
            if (!reader.hasNext()) {
                break;
            }
            event = reader.next();
        }
        return students;
    }

    private Group extractGroup(XMLStreamReader reader) throws XMLStreamException {
        Group group = new Group();
        int event = reader.getEventType();
        while (true) {
            if (event == START_ELEMENT) {
                if (reader.getAttributeCount() == 1) {
                    group.setId(Integer.parseInt(reader.getAttributeValue(0)));
                }
                String tagName = reader.getLocalName();
                if ("name".equals(tagName)) {
                    group.setName(reader.getElementText());
                }
                if ("department".equals(tagName)) {
                    group.setDepartment(reader.getElementText());
                }
                if ("baseCost".equals(tagName)) {
                    group.setBaseCost(new BigDecimal(reader.getElementText()));
                }
            }
            if (event == END_ELEMENT && "group".equals(reader.getLocalName())) {
                break;
            }
            if (!reader.hasNext()) {
                break;
            }
            event = reader.next();
        }
        return group;
    }

    private Student extractStudent(XMLStreamReader reader) throws XMLStreamException {
        Student student = new Student();
        int event = reader.getEventType();
        while (true) {
            if (event == START_ELEMENT) {
                String tagName = reader.getLocalName();
                if ("lastName".equals(tagName)) {
                    student.setLastName(reader.getElementText());
                }
                if ("firstName".equals(tagName)) {
                    student.setFirstName(reader.getElementText());
                }
                if ("patronymic".equals(tagName)) {
                    student.setPatronymic(reader.getElementText());
                }
                if ("birthDate".equals(tagName)) {
                    student.setBirthDate(LocalDate.parse(reader.getElementText()));
                }
                if ("age".equals(tagName)) {
                    student.setAge(Integer.parseInt(reader.getElementText()));
                }
                if ("onBudget".equals(tagName)) {
                    student.setOnBudget(Boolean.parseBoolean(reader.getElementText()));
                }
                if ("groupRef".equals(tagName)) {
                    student.setGroupRef(Integer.parseInt(reader.getElementText()));
                }
            }
            if (event == END_ELEMENT && "student".equals(reader.getLocalName())) {
                break;
            }
            if (!reader.hasNext()) {
                break;
            }
            event = reader.next();
        }
        return student;
    }
}
