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

public Map<String, Double> search(String query) {
    String[] terms = query.toLowerCase().split("\\s+");
    Map<String, Double> scores = new HashMap<>();
    int totalDocs = invertedIndex.getTotalDocuments();

    for (String term : terms) {

        Map<String, Integer> docs = invertedIndex.search(term);
        int df = invertedIndex.getDocumentFrequency(term);
        if (df == 0) continue;
        double idf = Math.log((double) totalDocs / df);

        for (Map.Entry<String, Integer> entry : docs.entrySet()) {

            String doc = entry.getKey();
            int tf = entry.getValue();
            double tfidf = tf * idf;
            scores.put(doc, scores.getOrDefault(doc, 0.0) + tfidf);
        }
    }

    return scores;
}

    public List<Map.Entry<String, Double>> rankResults(Map<String, Double> scores) 
    {
        List<Map.Entry<String, Double>> list = new ArrayList<>(scores.entrySet());
        list.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
        return list;
    }   
}

