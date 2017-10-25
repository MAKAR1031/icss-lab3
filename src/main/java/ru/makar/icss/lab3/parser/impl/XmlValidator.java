package ru.makar.icss.lab3.parser.impl;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stax.StAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class XmlValidator {
    public boolean validate(File xsd, File xml) {
        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(xml))) {
            XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(stream);
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(xsd);
            Validator validator = schema.newValidator();
            validator.validate(new StAXSource(reader));
            return true;
        } catch (SAXException | IOException | XMLStreamException e) {
            e.printStackTrace();
        }
        return false;
    }
}
