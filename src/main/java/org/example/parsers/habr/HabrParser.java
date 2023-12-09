package org.example.parsers.habr;

import org.example.parsers.Parser;
import org.example.parsers.habr.model.Article;
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
import java.util.ArrayList;

public class HabrParser implements Parser<ArrayList<Article>> {
    private static final String DEFAULT_FOLDER_PATH = "src\\images";
    private final String folderPath;

    public HabrParser(String targetPath) {
        this.folderPath = targetPath;
    }

    public HabrParser() {
        this.folderPath = DEFAULT_FOLDER_PATH;
    }

    @Override
    public ArrayList<Article> Parse(Document document) throws IOException {
        ArrayList<Article> articles = new ArrayList<>();
        Elements articleElements = document.select("article");

        createDirectoryIfNotExists(folderPath);

        for (Element articleElement : articleElements) {
            String title = articleElement.select("h2").text();
            String text = articleElement.select("div.article-formatted-body").text();
            String imageUrl = articleElement.select("img.tm-article-snippet__lead-image").attr("src");

            articles.add(Article.builder()
                    .title(title)
                    .text(text)
                    .imageUrl(imageUrl)
                    .build());

            downloadImage(imageUrl);
        }

        return articles;
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
