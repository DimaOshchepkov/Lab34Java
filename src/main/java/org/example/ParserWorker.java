package org.example;

import org.jsoup.nodes.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import parsers.Parser;
import parsers.ParserSettings;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

@Data
@AllArgsConstructor
public class ParserWorker<T> {
    private Parser<T> parser;
    private ParserSettings parserSettings;
    private HtmlLoader loader;
    private boolean isActive;
    private ArrayList<OnNewDataHandler<T>> onNewDataList = new ArrayList<>();
    private ArrayList<OnCompleted> onCompletedList = new ArrayList<>();

    public void start() throws IOException, ParseException {
        isActive = true;
        worker();
    }

    public ParserWorker(Parser<T> parser) {
        this.parser = parser;
    }

    public void abort() {
        isActive = false;
    }

    private void worker() throws IOException, ParseException {
        for (int i = parserSettings.getStartPoint(); i <= parserSettings.getEndPoint(); i++) {
            if (!isActive) {
                onCompletedList.get(0).onCompleted(this);
                return;
            }
            Document document = loader.GetSourceByPageId(i);
            T result = parser.Parse(document);
            onNewDataList.get(0).onNewData(this, result);
        }
        onCompletedList.get(0).onCompleted(this);
        isActive = false;
    }

    public void setParserSettings(ParserSettings parserSettings) {
        this.parserSettings = parserSettings;
        loader = new HtmlLoader(parserSettings);
    }

    public interface OnNewDataHandler<T> {
        void onNewData(Object sender, T e);
    }

    public interface OnCompleted {
        void onCompleted(Object sender);
    }
}
