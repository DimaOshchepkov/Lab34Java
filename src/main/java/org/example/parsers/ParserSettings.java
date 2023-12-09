package org.example.parsers;

import lombok.Getter;

@Getter
public abstract class ParserSettings {

    // Адрес сайта
    protected String BASE_URL;

    // префикс страницы
    protected String PREFIX;

    // начало пагинации
    protected int startPoint;

    // конец пагинации
    protected int endPoint;  
}

