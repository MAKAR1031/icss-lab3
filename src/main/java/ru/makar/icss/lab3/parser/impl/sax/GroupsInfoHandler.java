package ru.makar.icss.lab3.parser.impl.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.makar.icss.lab3.model.Group;
import ru.makar.icss.lab3.model.GroupsInfo;
import ru.makar.icss.lab3.model.Student;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GroupsInfoHandler extends DefaultHandler {

    private GroupsInfo info;

    private Group group;
    private boolean isName;
    private boolean isDepartment;
    private boolean isBaseCost;

    private Student student;
    private boolean isLastName;
    private boolean isFirstName;
    private boolean isPatronymic;
    private boolean isBirthDate;
    private boolean isAge;
    private boolean isOnBudget;
    private boolean isGroupRef;

    GroupsInfoHandler() {
        info = new GroupsInfo();
        group = new Group();
        student = new Student();
    }

    public GroupsInfo getInfo() {
        return info;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("group".equals(qName)) {
            group.setId(Integer.parseInt(attributes.getValue("id")));
        }
        if ("name".equals(qName)) {
            isName = true;
        }
        if ("department".equals(qName)) {
            isDepartment = true;
        }
        if ("baseCost".equals(qName)) {
            isBaseCost = true;
        }

        if ("lastName".equals(qName)) {
            isLastName = true;
        }
        if ("firstName".equals(qName)) {
            isFirstName = true;
        }
        if ("patronymic".equals(qName)) {
            isPatronymic = true;
        }
        if ("birthDate".equals(qName)) {
            isBirthDate = true;
        }
        if ("age".equals(qName)) {
            isAge = true;
        }
        if ("onBudget".equals(qName)) {
            isOnBudget = true;
        }
        if ("groupRef".equals(qName)) {
            isGroupRef = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        isName = false;
        isDepartment = false;
        isBaseCost = false;

        isLastName = false;
        isFirstName = false;
        isPatronymic = false;
        isBirthDate = false;
        isAge = false;
        isOnBudget = false;
        isGroupRef = false;

        if ("group".equals(qName)) {
            info.getGroups().add(group);
            group = new Group();
        }
        if ("student".equals(qName)) {
            info.getStudents().add(student);
            student = new Student();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = String.valueOf(ch, start, length);

        if (isName) {
            group.setName(value);
        }
        if (isDepartment) {
            group.setDepartment(value);
        }
        if (isBaseCost) {
            group.setBaseCost(new BigDecimal(value));
        }

        if (isLastName) {
            student.setLastName(value);
        }
        if (isFirstName) {
            student.setFirstName(value);
        }
        if (isPatronymic) {
            student.setPatronymic(value);
        }
        if (isBirthDate) {
            student.setBirthDate(LocalDate.parse(value));
        }
        if (isAge) {
            student.setAge(Integer.parseInt(value));
        }
        if (isOnBudget) {
            student.setOnBudget(Boolean.parseBoolean(value));
        }
        if (isGroupRef) {
            student.setGroupRef(Integer.parseInt(value));
        }
    }
}
