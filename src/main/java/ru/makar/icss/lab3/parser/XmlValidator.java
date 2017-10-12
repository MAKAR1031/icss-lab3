package ru.makar.icss.lab3.parser;

import java.io.File;

public interface XmlValidator {
    boolean validate(File xsd, File xml);
}
