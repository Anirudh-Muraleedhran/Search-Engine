//STATIC DOC INDEXING 
// package com.searchengine;

// import com.searchengine.search.SearchEngine;

// import java.util.Scanner;

// import com.searchengine.indexing.Indexer;
// import com.searchengine.indexing.InvertedIndex;
// import com.searchengine.indexing.Tokenizer;

// import java.util.Set;

// public class Main {

//     public static void main(String[] args) {

//         InvertedIndex invertedIndex = new InvertedIndex();
//         Tokenizer tokenizer = new Tokenizer();
//         Indexer indexer = new Indexer(invertedIndex,tokenizer);

//         indexer.indexDocument("data/documents");

//         SearchEngine searchEngine = new SearchEngine(invertedIndex);

//         Scanner scanner = new Scanner(System.in);

//         System.out.println("Search Engine is Ready");
//         System.out.println("Enter Search Term");

//         String query = scanner.nextLine();

//         Set<String> results = searchEngine.search(query);

//         System.out.println("Results :");

//         for(String doc : results)
//         {
//             System.out.println(doc);
//         }
//         scanner.close();
//     }
// }


// WEB CRAWLERS

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

            String query = scanner.nextLine();

            if (query.equalsIgnoreCase("exit")) {
                break;
            }

            Map<String, Integer> scores = searchEngine.search(query);
            List<Map.Entry<String, Integer>> ranked = searchEngine.rankResults(scores);
            
            if (ranked.isEmpty()) {
                System.out.println("No results found.");
            } else {
                System.out.println("Results:");

                for (Map.Entry<String, Integer> entry : ranked) {
                    System.out.println(entry.getKey() + " (score: " + entry.getValue() + ")");
                }
            }
        }

        scanner.close();
    }
}