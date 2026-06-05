package searchengineapi.crawler;

import searchengineapi.indexing.Indexer;
import searchengineapi.indexing.InvertedIndex;
import searchengineapi.indexing.Tokenizer;
import searchengineapi.indexing.InvertedIndex;

import java.util.*;

public class WebCrawler {
    public Set<String> visitedURL;
    private Queue<String> urlQueue;

    private PageFetcher pageFetcher;
    private LinkExtractor linkExtractor;

    private int maxPages;
    private Indexer indexer;
    //constructor
    public WebCrawler(int maxPages,InvertedIndex invertedIndex)
    {
        this.maxPages = maxPages;

        visitedURL = new HashSet<>();
        urlQueue = new LinkedList<>();

        pageFetcher = new PageFetcher();
        linkExtractor = new LinkExtractor();

        Tokenizer tokenizer = new Tokenizer();

        this.indexer = new Indexer(invertedIndex, tokenizer);
    }

    public void startCrawling(String seedURL)
    {
        urlQueue.add(seedURL);
        //checks queue
        while(!urlQueue.isEmpty() && visitedURL.size()<maxPages)
        {
            String currentURL = urlQueue.poll();
            if(visitedURL.contains(currentURL))
            {
                continue;
            }

            System.out.println("Crawling: "+ currentURL);

            visitedURL.add(currentURL);
            
            String html = pageFetcher.fetch(currentURL);

            if (html != null) {

                String text = html.replaceAll("<[^>]*>", " ").toLowerCase();
                indexer.indexText(currentURL, text);

                Set<String> links = linkExtractor.extractLinks(html);

                for (String link : links) {
                    if (!visitedURL.contains(link)) {
                        urlQueue.add(link);
                    }
                }
            }
        }
    }

    public InvertedIndex getInvertedIndex() {
        return indexer.getInvertedIndex();
    }
}