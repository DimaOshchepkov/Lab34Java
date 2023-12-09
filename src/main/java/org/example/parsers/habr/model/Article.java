package org.example.parsers.habr.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@Builder
public class Article {
    private final String title;
    private final String text;
    private final String imageUrl;
}
