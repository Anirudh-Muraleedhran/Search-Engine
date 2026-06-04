package com.searchengine.indexing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Collections;

public class InvertedIndex{
    private int totalDocuments = 0;
    private Set<String> allDocuments = new HashSet<>();

    private Map<String,Map<String,List<Integer>>>index;
    public InvertedIndex(){
        this.index = new HashMap<>();
    }

    public void addDocument(String documentId, List<String> tokens) 
    {

        if (!allDocuments.contains(documentId)) {
            totalDocuments++;
            allDocuments.add(documentId);
        }

        for(int position = 0;position<tokens.size();position++)
        {
            String token = tokens.get(position);
            index.putIfAbsent(token, new HashMap<>());
            Map<String,List<Integer>> docMap = index.get(token);
            docMap.putIfAbsent(documentId, new ArrayList<>());
            docMap.get(documentId).add(position);
        }
    }
    
    public int getTotalDocuments() 
    {
        return totalDocuments;
    }

    public int getDocumentFrequency(String term)
    {
        Map<String,List<Integer>> docs = index.get(term);
        return docs == null ? 0 : docs.size();
    }

    public Map<String,List<Integer>> search(String term)
    {
        return index.getOrDefault(term, Collections.emptyMap());
    }

    public void printIndex()
    {
        for(Map.Entry<String,Map<String,List<Integer>>> entry : index.entrySet())
        {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    public int getTermFrequency(String term,String documentId)
    {
        Map<String,List<Integer>> docs = index.get(term);
        if(docs == null)
        {
            return 0;
        }

        List<Integer> positions = docs.get(documentId);

        if(posistions == null)
        {
            return 0;
        }

        return positions.size();
    }

    public List<Integer> getPositions(String term,String documentId)
    {
        Map<String,List<Integer>> docs = index.get(term);
        if(docs == null)
        {
            return Collections.emptyList();
        }
        return docs.getOrDefault(documentId, Collections.emptyList());
    }

}