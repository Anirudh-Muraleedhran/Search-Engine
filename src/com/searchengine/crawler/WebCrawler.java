package com.searchengine.crawler;

import java.util.*;

public class WebCrawler {
    public Set<String> visitedURL;
    private Queue<String> urlQueue;

    private PageFetcher pageFetcher;
    private LinkExtractor linkExtractor;

    private int maxPages;

    //constructor
    public WebCrawler(int maxPages)
    {
        this.maxPages = maxPages;

        visitedURL = new HashSet<>();
        urlQueue = new LinkedList<>();

        pageFetcher = new PageFetcher();
        linkExtractor = new LinkExtractor();
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

            Set <String> links = linkExtractor.extractLinks(html);

            for(String link : links)
            {
                if(!visitedURL.contains(link))
                {
                    urlQueue.add(link);
                }
            }
        }
    }
}