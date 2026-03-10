package com.searchengine;

import com.searchengine.crawler.WebCrawler;

public class Main {

    public static void main(String[] args) {

        WebCrawler crawler = new WebCrawler(10);

        crawler.startCrawling("https://example.com");
    }
}