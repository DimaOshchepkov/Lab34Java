package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImageLoader {

    public void downloadImage(String imageUrl, String folderPath) {
        if (imageUrl.startsWith("https")) {
            try {
                URL url = new URL(imageUrl);
                String fileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
                Path imagePath = Paths.get(folderPath, fileName);

                try (InputStream in = url.openStream()) {
                    Files.copy(in, imagePath, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
