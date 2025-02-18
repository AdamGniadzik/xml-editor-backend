package com.ironbark.xml.editor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class XmlEditorBackendApplication {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        File file = new File("src/main/resources/simpleType.xsd");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(file);
        System.out.println(document.getChildNodes());
        SpringApplication.run(XmlEditorBackendApplication.class, args);

    }

}
