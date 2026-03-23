package com.searchengine.search;

import com.searchengine.indexing.InvertedIndex;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchEngine{

    private InvertedIndex invertedIndex;

    public SearchEngine(InvertedIndex invertedIndex)
    {
        this.invertedIndex = invertedIndex;
    }

    public Map<String, Integer> search(String query) {

    String[] terms = query.toLowerCase().split("\\s+");

    Map<String, Integer> scores = new HashMap<>();

    for (String term : terms) {

        Map<String, Integer> docs = invertedIndex.search(term);

        for (Map.Entry<String, Integer> entry : docs.entrySet()) 
            {
                String url = entry.getKey();
                int freq = entry.getValue();

                scores.put(url, scores.getOrDefault(url, 0) + freq);
            }
        }
        return scores;
    }

    public List<Map.Entry<String, Integer>> rankResults(Map<String, Integer> scores) 
    {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());
        list.sort((a, b) -> b.getValue() - a.getValue());
        return list;
    }
}

