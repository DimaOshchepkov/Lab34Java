package org.example.parsers;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.ParseException;

public interface Parser<T> {
    T parse(Document document) throws IOException, ParseException;
}

