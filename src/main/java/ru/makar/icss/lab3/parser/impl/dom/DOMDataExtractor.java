package ru.makar.icss.lab3.parser.impl.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.makar.icss.lab3.model.Group;
import ru.makar.icss.lab3.model.GroupsInfo;
import ru.makar.icss.lab3.model.Student;

import java.math.BigDecimal;
import java.time.LocalDate;

final class DOMDataExtractor {
    GroupsInfo extractInfo(Document document) {
        GroupsInfo info = new GroupsInfo();
        Element root = document.getDocumentElement();
        NodeList nodes = root.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            if ("groups".equals(node.getNodeName())) {
                extractGroups(info, node);
            }
            if ("students".equals(node.getNodeName())) {
                extractStudents(info, node);
            }
        }
        return info;
    }

    private void extractGroups(GroupsInfo info, Node node) {
        NodeList groupNodes = node.getChildNodes();
        for (int j = 0; j < groupNodes.getLength(); j++) {
            Node groupNode = groupNodes.item(j);
            if (groupNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Group group = new Group();
            String groupId = groupNode.getAttributes().getNamedItem("id").getNodeValue();
            group.setId(Integer.parseInt(groupId));
            NodeList groupDataNodes = groupNode.getChildNodes();
            for (int k = 0; k < groupDataNodes.getLength(); k++) {
                Node groupDataNode = groupDataNodes.item(k);
                if (groupDataNode.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                if ("name".equals(groupDataNode.getNodeName())) {
                    group.setName(getNodeValue(groupDataNode));
                }
                if ("department".equals(groupDataNode.getNodeName())) {
                    group.setDepartment(getNodeValue(groupDataNode));
                }
                if ("baseCost".equals(groupDataNode.getNodeName())) {
                    group.setBaseCost(new BigDecimal(getNodeValue(groupDataNode)));
                }
            }
            info.getGroups().add(group);
        }
    }

    private void extractStudents(GroupsInfo info, Node node) {
        NodeList studentNodes = node.getChildNodes();
        for (int j = 0; j < studentNodes.getLength(); j++) {
            Node studentNode = studentNodes.item(j);
            if (studentNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Student student = new Student();
            NodeList studentDataNodes = studentNode.getChildNodes();
            for (int k = 0; k < studentDataNodes.getLength(); k++) {
                Node studentDataNode = studentDataNodes.item(k);
                if (studentDataNode.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                if ("lastName".equals(studentDataNode.getNodeName())) {
                    student.setLastName(getNodeValue(studentDataNode));
                }
                if ("firstName".equals(studentDataNode.getNodeName())) {
                    student.setFirstName(getNodeValue(studentDataNode));
                }
                if ("patronymic".equals(studentDataNode.getNodeName())) {
                    student.setPatronymic(getNodeValue(studentDataNode));
                }
                if ("birthDate".equals(studentDataNode.getNodeName())) {
                    student.setBirthDate(LocalDate.parse(getNodeValue(studentDataNode)));
                }
                if ("age".equals(studentDataNode.getNodeName())) {
                    student.setAge(Integer.parseInt(getNodeValue(studentDataNode)));
                }
                if ("onBudget".equals(studentDataNode.getNodeName())) {
                    student.setOnBudget(Boolean.parseBoolean(getNodeValue(studentDataNode)));
                }
                if ("groupRef".equals(studentDataNode.getNodeName())) {
                    student.setGroupRef(Integer.parseInt(getNodeValue(studentDataNode)));
                }
            }
            info.getStudents().add(student);
        }
    }

    private String getNodeValue(Node node) {
        return node.getChildNodes().item(0).getNodeValue();
    }
}
