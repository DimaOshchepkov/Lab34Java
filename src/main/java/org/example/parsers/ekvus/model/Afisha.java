package org.example.parsers.ekvus.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
public class Afisha {
    private final String title;
    private final String date;
    private final String ageLimit;
    private final String imageUrl;
    private final String duration;

    @Override
    public String toString() {
        return """
                %ntitle= %s
                date= %s
                imageUrl= %s
                duration= %s
                ageLimit= %s%n
                """.formatted(title, date, imageUrl, duration, ageLimit);
    }
}
