package org.example;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.example.parsers.Completed;
import org.example.parsers.NewData;
import org.example.parsers.ekvus.EkvusParser;
import org.example.parsers.ekvus.EkvusSettings;
import org.example.parsers.ekvus.model.Afisha;
import org.example.parsers.habr.HabrParser;
import org.example.parsers.habr.HabrSettings;
import org.example.parsers.habr.model.Article;
import org.example.parsers.theatre.TheatreParser;
import org.example.parsers.theatre.TheatreSettings;
import org.example.parsers.theatre.model.Poster;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ParseException {

    
        ParserWorker<ArrayList<Afisha>> parser = new ParserWorker<>(new EkvusParser());
        parser.setParserSettings(new EkvusSettings());
        parser.getOnCompletedList().add(new Completed());
        parser.getOnNewDataList().add(new NewData());

        parser.start();
        Thread.sleep(10000);
        parser.abort();
    }
}