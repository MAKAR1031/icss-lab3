package ru.makar.icss.lab3.parser;

import ru.makar.icss.lab3.model.GroupsInfo;

import java.io.File;

public interface XmlParser {
    GroupsInfo parse(File xml);
}
