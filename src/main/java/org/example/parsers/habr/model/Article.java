package org.example.parsers.habr.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Article {
    private final String title;
    private final String text;
    private final String imageUrl;

    @Override
    public String toString() { 
        return """
                title=%s
                text=%s
                imageURL=%s
                """.formatted(title, text, imageUrl);
    }
}