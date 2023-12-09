package org.example;

import org.example.parsers.ParserSettings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class HtmlLoader {

    String url;

    public HtmlLoader(ParserSettings settings){
        url = settings.getBASE_URL()+"/"+ settings.getPREFIX();
    }

    public Document GetSourceByPageId(int id) throws IOException {
        String currentUrl = url.replace("{CurrentId}", Integer.toString(id));
        return Jsoup.connect(currentUrl).get();
    }
}

