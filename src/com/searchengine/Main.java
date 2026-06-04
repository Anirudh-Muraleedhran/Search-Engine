package com.searchengine;

import com.searchengine.crawler.WebCrawler;
import com.searchengine.search.SearchEngine;
import com.searchengine.indexing.InvertedIndex;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        WebCrawler crawler = new WebCrawler(10);

        crawler.startCrawling("https://en.wikipedia.org/wiki/Search_engine");

        InvertedIndex index = crawler.getInvertedIndex();

        SearchEngine searchEngine = new SearchEngine(index);

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("\nEnter search term (or 'exit'):");

            String query = scanner.nextLine().trim();

            if (query.equalsIgnoreCase("exit")) {
                break;
            }

            if (query.startsWith("\"") && query.endsWith("\""))
            {
                String phrase = query.substring(1,query.length() - 1).trim();

                Set<String> results =searchEngine.phraseSearch(phrase);

                if (results.isEmpty())
                {
                    System.out.println("No phrase matches found.");
                }
                else
                {
                    System.out.println("Phrase Matches:");

                    for (String doc : results)
                    {
                        System.out.println(doc);
                    }
                }
            }
            else
            {
                Map<String, Double> scores =searchEngine.search(query);

                List<Map.Entry<String, Double>> ranked =searchEngine.rankResults(scores);

                if (ranked.isEmpty())
                {
                    System.out.println("No results found.");
                }
                else
                {
                    System.out.println("Results:");

                    for (Map.Entry<String, Double> entry : ranked)
                    {
                        System.out.println(entry.getKey()+ " (score: "+ entry.getValue()+ ")");
                    }
                }
            }
        }

        scanner.close();
    }
}