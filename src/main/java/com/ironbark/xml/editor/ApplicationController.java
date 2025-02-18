package com.ironbark.xml.editor;

import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class ApplicationController {

    private final Parser parser;

    @PostMapping
    public ResponseEntity<JsonElement> parseXsdZipFile(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println(file.getContentType());
        return ResponseEntity.ok(parser.parseXsdFiles(file.getBytes()));
    }
}
