package org.example;

import org.example.parsers.Parser;
import org.example.parsers.ParserSettings;
import org.jsoup.nodes.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;


@Data
@AllArgsConstructor
public class ParserWorker<T> extends SwingWorker<Void, T> {
    private Parser<T> parser;
    private ParserSettings parserSettings;
    private HtmlLoader loader;
    private ArrayList<OnNewDataHandler<T>> onNewDataList = new ArrayList<>();
    private ArrayList<OnCompleted> onCompletedList = new ArrayList<>();


    public ParserWorker(Parser<T> parser) {
        this.parser = parser;
    }

    public ParserWorker(Parser<T> parser, ParserSettings settings) {
        this.parser = parser;
        this.parserSettings = settings;
        loader = new HtmlLoader(settings);
    }

    @Override
    protected Void doInBackground() throws Exception {
        for (int i = parserSettings.getStartPoint(); i <= parserSettings.getEndPoint(); i++) {
            if (isCancelled()) {
                return null;
            }
    
            try {
                Document document = loader.GetSourceByPageId(i);
                T result = parser.parse(document);
                publish(result); // Публикуем промежуточный результат для обновления GUI
            } catch (IOException | ParseException e) {
                // Обработка ошибок при неудачной загрузке или парсинге
            }
        }
        return null;
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

    @Override
    protected void process(List<T> chunks) {
        // Обновление GUI на основе полученных результатов
        for (T result : chunks) {
            onNewDataList.get(0).onNewData(this, result);
        }
    }

    @Override
    protected void done() {
        onCompletedList.get(0).onCompleted(this);
    }
}
