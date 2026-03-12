package com.searchengine;

import com.searchengine.search.SearchEngine;

import java.util.Scanner;

import com.searchengine.indexing.Indexer;
import com.searchengine.indexing.InvertedIndex;
import com.searchengine.indexing.Tokenizer;

import java.util.Set;

public class Main {

    public static void main(String[] args) {

        InvertedIndex invertedIndex = new InvertedIndex();
        Tokenizer tokenizer = new Tokenizer();
        Indexer indexer = new Indexer(invertedIndex,tokenizer);

        indexer.indexDocument("data/documents");

        SearchEngine searchEngine = new SearchEngine(invertedIndex);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Search Engine is Ready");
        System.out.println("Enter Search Term");

        String query = scanner.nextLine();

        Set<String> results = searchEngine.search(query);

        System.out.println("Results :");

        for(String doc : results)
        {
            System.out.println(doc);
        }
        scanner.close();
    }
}