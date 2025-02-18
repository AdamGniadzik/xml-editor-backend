package com.ironbark.xml.editor;

import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;


@Component
@Slf4j
public class FileLoader {
    private final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    private final DocumentBuilder db = dbf.newDocumentBuilder();


    public FileLoader() throws ParserConfigurationException {
    }

    public List<Document> loadDocumentsFromZip(byte[] zipBytes, UUID uuid) {
        try {
            List<Document> documents = new ArrayList<>();
            Path path = Files.createDirectory(Path.of(System.getProperty("java.io.tmpdir") + uuid));
            File zip = new File(path + "/zip");
            FileOutputStream o = new FileOutputStream(zip);
            o.write(zipBytes);
            o.close();
            String destination = path + "/content";
            try {
                ZipFile zipFile = new ZipFile(zip);
                zipFile.extractAll(destination);
            } catch (ZipException e) {
                e.printStackTrace();
            }
            Stream<Path> paths = Files.walk(Paths.get(path + "/content"));
            paths
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            documents.add(getDocumentFromFile(file.toFile()));
                        } catch (IOException | SAXException e) {
                            throw new RuntimeException(e);
                        }
                    });
            return documents;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ParseInterruptedException();
        } finally {
            String directoryPath = System.getProperty("java.io.tmpdir") + uuid;
            File fileToDelete = new File(directoryPath);
            deleteFile(fileToDelete);
        }
    }

    private void deleteFile(File file){
        if(file.isDirectory()){
            for(File f: file.listFiles()){
                deleteFile(f);
            }
            file.delete();
        }else{
            file.delete();
        }
    }

    private Document getDocumentFromFile(File file) throws IOException, SAXException {
        return db.parse(file);
    }
}
