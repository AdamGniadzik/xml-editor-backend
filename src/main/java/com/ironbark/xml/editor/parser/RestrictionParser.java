package com.ironbark.xml.editor.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.ironbark.xml.editor.util.NodeListIterable;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import static com.ironbark.xml.editor.parser.ParserOutputConstants.*;
import static com.ironbark.xml.editor.parser.XsdSchemaConstants.*;

@Component
class RestrictionParser {

    private JsonElement parseRestriction(Element element) {
        JsonObject jsonElement = new JsonObject();
        NodeListIterable nodeList = NodeListIterable.of(element.getChildNodes());
        for (Node node : nodeList) {
            addProperty(jsonElement, (Element) node);
        }

        return jsonElement;
    }


    private void addProperty(JsonObject json, Element element) {
        switch (element.getNodeName()) {
            case XSD_MAX_LENGTH -> json.add(MAX_LENGTH, new JsonPrimitive(element.getAttribute(XSD_VALUE_ATTR)));
        }
    }


}
