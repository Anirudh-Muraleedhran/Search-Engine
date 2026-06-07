package searchengineapi.crawler;

import searchengineapi.indexing.Indexer;
import searchengineapi.indexing.InvertedIndex;
import searchengineapi.indexing.Tokenizer;
import searchengineapi.indexing.InvertedIndex;
import searchengineapi.service.DocumentMetadataStore;
import searchengineapi.model.DocumentMetadata;

import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebCrawler {
    public Set<String> visitedURL;
    private Queue<String> urlQueue;

    private PageFetcher pageFetcher;
    private LinkExtractor linkExtractor;

    private int maxPages;
    private Indexer indexer;

    private DocumentMetadataStore metadataStore;
    //constructor
    public WebCrawler(int maxPages,InvertedIndex invertedIndex,DocumentMetadataStore metadataStore)
    {
        this.maxPages = maxPages;
        this.metadataStore = metadataStore;

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

            if (html != null) 
            {
                Document doc = Jsoup.parse(html);
                String title = doc.title();
                String text = doc.body() != null? doc.body().text(): "";
                String snippet =text.length() > 200? text.substring(0, 200): text;

                metadataStore.addMetaData(new DocumentMetadata(currentURL,title,snippet));

                indexer.indexText(currentURL,text.toLowerCase());

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