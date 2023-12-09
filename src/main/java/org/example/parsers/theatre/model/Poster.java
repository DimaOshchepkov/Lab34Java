package org.example.parsers.theatre.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@Builder
public class Poster {
    private final String title;
    private final String imageUrl;
    private final String date;
    private final String duration;
    private final String ageLimit;

    @Override
    public String toString() {
        return """
               title= %s
               imageUrl= %s
               date= %s
               duration= %s
               ageLimit= %s
               """.formatted(title, imageUrl, date, duration, ageLimit);
    }

}
