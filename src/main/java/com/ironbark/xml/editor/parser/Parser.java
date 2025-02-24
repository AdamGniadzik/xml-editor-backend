package com.ironbark.xml.editor.parser;

import com.google.gson.*;
import com.ironbark.xml.editor.FileLoader;
import com.ironbark.xml.editor.ImportHandler;
import com.ironbark.xml.editor.util.NamedNodeMapIterable;
import com.ironbark.xml.editor.util.NodeListIterable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.*;

import static com.ironbark.xml.editor.parser.ParserOutputConstants.*;
import static com.ironbark.xml.editor.parser.XsdSchemaConstants.*;

@Component
@AllArgsConstructor
public class Parser {


    private static final int SEMICOLON_LENGTH = 1;
    private final ImportHandler importHandler;
    private final Gson gson = new Gson();
    private final FileLoader fileLoader;




    public JsonElement parseXsdFiles(byte[] zipBytes) {
        try {
            JsonArray output = new JsonArray();
            Map<Integer, Map<String, String>> documentNamespaceBucket = new HashMap<>();
            Queue<NodeWrapper> nodeQueue = new LinkedList<>();
            List<Document> documents = fileLoader.loadDocumentsFromZip(zipBytes, UUID.randomUUID());
            for (int documentId = 0; documentId < documents.size(); documentId++) {
                Document document = documents.get(documentId);
                String targetNamespace = getTargetNamespace(document);
                documentNamespaceBucket.put(documentId, getNamespacesFromRootElement(document));
                NodeListIterable nodes = NodeListIterable.of(document.getDocumentElement().getChildNodes());
                for (Node node : nodes) {
                    if (node instanceof Element) {
                        nodeQueue.add(new NodeWrapper((Element) node, targetNamespace, documentId));
                    }
                }
            }

            while(!nodeQueue.isEmpty()){
                    NodeWrapper node = nodeQueue.poll();
                    if(node.element.getNodeName().equals(XSD_ELEMENT)){
                        output.add(parseElement(node.element));
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private Map<String, String> getNamespacesFromRootElement(Document document) {
        Map<String, String> bucket = new HashMap<>();
        NamedNodeMapIterable attributes = NamedNodeMapIterable.of(document.getDocumentElement().getAttributes());
        attributes.forEach(attribute -> {
            if (attribute.getNodeName().startsWith(XMLNS)) {
                bucket.put(attribute.getNodeName()
                        .substring(XMLNS.length() + SEMICOLON_LENGTH), attribute.getNodeValue());
            }
        });
        return bucket;
    }

    private String getTargetNamespace(Document document) {
        String tmp = document.getDocumentElement().getAttribute(TARGET_NAMESPACE)
                .substring(TARGET_NAMESPACE.length() + SEMICOLON_LENGTH);
        return tmp.isEmpty() ? null : tmp;
    }

    private JsonElement parseElement(Element element) {
        JsonObject jsonElement = new JsonObject();
        jsonElement.add(NAME, new JsonPrimitive(element.getAttribute(NAME)));
        jsonElement.add(TYPE, new JsonPrimitive(element.getAttribute(TYPE)));
        jsonElement.add(TYPE, new JsonPrimitive(element.getAttribute(TYPE)));
        System.out.println(jsonElement);
        return jsonElement;
    }


    public record NodeWrapper(Element element, String targetNamespace, int documentId) {
    }

}
