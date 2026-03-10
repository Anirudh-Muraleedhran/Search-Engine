package com.searchengine.crawler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class PageFetcher {

    public String fetch(String urlString) {

        StringBuilder content = new StringBuilder();

        try {

            URL url = new URL(urlString);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );

            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Failed To Fetch Page: " + urlString);
        }

        return content.toString();
    }
}