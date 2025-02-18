package com.ironbark.xml.editor;

public class ParseInterruptedException extends RuntimeException{
    public ParseInterruptedException() {
        super("Parsing zip file has been interrupted by unexpected error.");
    }
}
