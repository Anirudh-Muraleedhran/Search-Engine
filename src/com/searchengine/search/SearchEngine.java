package com.searchengine.search;

import com.searchengine.indexing.InvertedIndex;

import java.util.Set;

public class SearchEngine{

    private InvertedIndex invertedIndex;

    public SearchEngine(InvertedIndex invertedIndex)
    {
        this.invertedIndex = invertedIndex;
    }

    public Set<String> search(String term)
    {
        term = term.toLowerCase();

        return invertedIndex.search(term);
    }
}

