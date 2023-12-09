package parsers.habr.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Article {
    private final String title;
    private final String text;
    private final String imageUrl;
}
