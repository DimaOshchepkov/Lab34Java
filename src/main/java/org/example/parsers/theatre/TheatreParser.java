package org.example.parsers.theatre;

import org.example.ImageLoader;
import org.example.parsers.Parser;
import org.example.parsers.theatre.model.Poster;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.ArrayList;

public class TheatreParser implements Parser<ArrayList<Poster>> {

    private static final String DEFAULT_FOLDER_PATH = "src\\images";

    private final String folderPath;

    ImageLoader imageLoader = new ImageLoader();

    public TheatreParser(String targetPath) {
        this.folderPath = targetPath;
    }

    public TheatreParser() {
        this.folderPath = DEFAULT_FOLDER_PATH;
    }

    @Override
    public ArrayList<Poster> parse(Document document) {
        ArrayList<Poster> posters = new ArrayList<>();
        Elements postersElements = document.select("div.t_afisha");

        createDirectoryIfNotExists(folderPath);

        for (Element poster : postersElements) {
            try {
                Poster parsedPoster = parsePoster(poster);
                imageLoader.downloadImage(parsedPoster.getImageUrl(), folderPath);
                posters.add(parsedPoster);
            } catch (ParseException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return posters;
    }

    private Poster parsePoster(Element poster) throws ParseException {
        String duration = poster.select(".td3 .td2 .td1 div").first().textNodes().get(0).text();
        Element td2Element = poster.select(".td2").first();
        String imageUrl = td2Element.select("img").first().absUrl("src");

        Element tInfoAfishaElement = poster.select(".t_info_afisha").first();
        String date = tInfoAfishaElement.select(".td1 .date_afisha").text();
        String title = tInfoAfishaElement.select("h3 a").textNodes().get(0).text();
        String ageLimit = tInfoAfishaElement.select(".value_limit").text();

        return Poster.builder()
                .title(title)
                .date(date)
                .ageLimit(ageLimit)
                .duration(duration)
                .imageUrl(imageUrl)
                .build();
    }

    private void createDirectoryIfNotExists(String folderPath) {
        try {
            Files.createDirectories(Paths.get(folderPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

