package com.ironbark.xml.editor.util;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.util.Iterator;

public final class NodeListIterable implements Iterable<Node> {

    private final NodeList nodeList;

    private NodeListIterable(NodeList nodeList) {
        this.nodeList = nodeList;
    }

    public static NodeListIterable of(NodeList nodeList) {
        return new NodeListIterable(nodeList);
    }

    private class NodeListIterator implements Iterator<Node> {

        private int nextIndex = 0;

        @Override
        public boolean hasNext() {
            return (nodeList.getLength() > nextIndex);
        }
        @Override
        public Node next() {
            Node item = nodeList.item(nextIndex);
            nextIndex = nextIndex + 1;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    @Override
    public Iterator<Node> iterator() {
        return new NodeListIterator();
    }
}