package ru.makar.icss.lab3.parser;

import ru.makar.icss.lab3.model.GroupsInfo;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public interface DataExtractor {
    GroupsInfo extractInfo(XMLStreamReader reader) throws XMLStreamException;
}
