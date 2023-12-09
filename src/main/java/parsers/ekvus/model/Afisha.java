package parsers.ekvus.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@Builder
public class Afisha {
    private final String title;
    private final String date;
    private final String ageLimit;
    private final String imageUrl;
    private final String duration;
}
