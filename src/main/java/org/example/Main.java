package org.example;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.example.gui.GUI;
import org.example.parsers.Completed;
import org.example.parsers.NewData;
import org.example.parsers.ParserSettings;
import org.example.parsers.ekvus.EkvusParser;
import org.example.parsers.ekvus.EkvusSettings;
import org.example.parsers.ekvus.model.Afisha;
import org.example.parsers.habr.HabrParser;
import org.example.parsers.habr.HabrSettings;
import org.example.parsers.habr.model.Article;
import org.example.parsers.theatre.TheatreParser;
import org.example.parsers.theatre.TheatreSettings;
import org.example.parsers.theatre.model.Poster;

import org.example.parsers.Parser;

import lombok.val;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
/*
        Parser par = new EkvusParser();
        ParserSettings settings = new EkvusSettings();
        ParserWorker.OnCompleted completed = new Completed();
        ParserWorker.OnNewDataHandler newDataHandler = new NewData<>();

        Scanner scanner = new Scanner(System.in);

        String[] urls = {
                "https://ekvus-kirov.ru/afisha",
                "https://habr.com/ru/all",
                "https://kirovdramteatr.ru/afisha"
        };

        for (int i = 0; i < urls.length; i++) {
            System.out.println((i + 1) + ". " + urls[i]);
        }

        String choice = inputString(scanner, "Выберите сайт для парсинга (введите номер от 1 до 3):");

        switch (choice) {
            case "1" -> {
                par = new EkvusParser();
                settings = new EkvusSettings();
            }

            case "2" -> {
                par = new HabrParser();
                settings = new HabrSettings(1, 2);
            }
            case "3" -> {
                par = new TheatreParser();
                settings = new TheatreSettings();
            }
            default -> {
                System.out.println("Неверный ввод");
            }
        }

        ParserWorker<ArrayList<Afisha>> parser = new ParserWorker<>(par);
        parser.setParserSettings(settings);
        parser.getOnCompletedList().add(completed);
        parser.getOnNewDataList().add(newDataHandler);

        parser.execute();
        //Thread.sleep(10000);
        parser.abort();

*/
        GUI form = new GUI();
        form.setVisible(true);
    }

    public static String inputString(Scanner scanner, String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}