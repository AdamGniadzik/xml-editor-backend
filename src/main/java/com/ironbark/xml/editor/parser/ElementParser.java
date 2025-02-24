package com.ironbark.xml.editor.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

import static com.ironbark.xml.editor.parser.ParserOutputConstants.NAME;
import static com.ironbark.xml.editor.parser.ParserOutputConstants.TYPE;

@Component
class ElementParser {


    private JsonElement parseElement(Element element) {
        JsonObject jsonElement = new JsonObject();
        jsonElement.add(NAME, new JsonPrimitive(element.getAttribute(NAME)));
        jsonElement.add(TYPE, new JsonPrimitive(element.getAttribute(TYPE)));
        jsonElement.add(TYPE, new JsonPrimitive(element.getAttribute(TYPE)));
        System.out.println(jsonElement);
        return jsonElement;
    }


}
