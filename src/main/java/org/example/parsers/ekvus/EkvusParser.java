package org.example.parsers.ekvus;

import org.apache.log4j.Logger;
import org.example.parsers.Parser;
import org.example.parsers.ekvus.model.Afisha;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;


public class EkvusParser implements Parser<ArrayList<Afisha>> {

    private static final Logger log = Logger.getLogger(EkvusParser.class);

    private static final String DEFAULT_FOLDER_PATH = "src\\images";
    private final String folderPath;

    public EkvusParser(String targetPath) {
        this.folderPath = targetPath;
    }

    public EkvusParser() {
        this.folderPath = DEFAULT_FOLDER_PATH;
    }

    @Override
    public ArrayList<Afisha> Parse(Document document) throws IOException {

        Elements postersElements = document.select("*.page_box")
                .select("tr:has(*:gt(2))");

        createDirectoryIfNotExists(folderPath);

        ArrayList<Afisha> posters = new ArrayList<>();
        int ind = 0;
        for (Element poster : postersElements) {
            log.debug("Обрабатывается пост" + Integer.toString(ind)); ind++;
            String date = poster.select("td:eq(0) > a > font").text();
            String title = poster.select("td:eq(1) > a").text();
            String ageLimit = poster.select("td:eq(1) > a > span").text();

            Document posterDocument = loadPerfomance(poster);
            String duration = getDuration(posterDocument);
            String imageUrl = getImageUrl(posterDocument);

            posters.add(Afisha.builder()
                    .title(title)
                    .date(date)
                    .ageLimit(ageLimit)
                    .duration(duration)
                    .imageUrl(imageUrl)
                    .build());

            downloadImage(imageUrl);
        }
        return posters;
    }

    private Document loadPerfomance(Element afisha) throws IOException {
        String href = afisha.select("td:eq(1) > a").attr("href");
        return Jsoup.connect("https://ekvus-kirov.ru" + href).get();
    }

    private String getDuration(Document doc) {

        String dur = "Продолжительность спектакля:";
        Elements durations = doc.getElementsByClass("page_box").first()
                .getElementsMatchingText(dur);

        return (!durations.isEmpty()) ? durations.last().text().substring(dur.length() + 1) : "";
    }

    private String getImageUrl(Document doc) {
        String imageUrl = "";
        Element image = doc.getElementById("photo_osnova");
        if (image != null) {
            imageUrl = image.absUrl("src");
        } else if (doc.getElementsByClass("img_right").first() != null) {
            imageUrl = doc.getElementsByClass("img_right").first().absUrl("src");
        }
        return imageUrl;
    }

    private void createDirectoryIfNotExists(String folderPath) throws IOException {
        Path path = Paths.get(folderPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    private void downloadImage(String imageUrl) throws IOException {
        if (imageUrl.startsWith("https")) {
            URL url = new URL(imageUrl);
            String fileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
            Path imagePath = Paths.get(folderPath, fileName);

            try (InputStream in = url.openStream()) {
                Files.copy(in, imagePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
