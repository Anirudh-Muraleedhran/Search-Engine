package searchengineapi.crawler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PageFetcher {

    public String fetch(String urlString) {

        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            int status = conn.getResponseCode();

            if (status != 200) {
                System.out.println("Failed To Fetch Page: " + urlString + " (Status: " + status + ")");
                return null;
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Error fetching: " + urlString);
            e.printStackTrace();
            return null;
        }

        return content.toString();
    }
}