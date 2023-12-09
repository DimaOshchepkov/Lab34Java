package org.example.parsers.theatre;

import org.example.parsers.ParserSettings;

public class TheatreSettings extends ParserSettings {

    public TheatreSettings(){
        startPoint = 1;
        endPoint = 1;
        BASE_URL = "https://kirovdramteatr.ru/afisha";
        PREFIX = "";
    }
}
