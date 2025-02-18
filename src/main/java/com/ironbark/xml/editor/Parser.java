package com.ironbark.xml.editor;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class Parser {
    private final ImportHandler importHandler;
    private final Gson gson = new Gson();
    private final FileLoader fileLoader;

    public JsonElement parseXsdFiles(byte[] zipBytes) {
        try {
            List<Document> documents = fileLoader.loadDocumentsFromZip(zipBytes, UUID.randomUUID());
            System.out.println(documents.size());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

}
