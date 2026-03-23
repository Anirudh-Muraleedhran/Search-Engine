package com.searchengine.indexing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InvertedIndex{
    private Map<String,Map<String,Integer>>index;
    public InvertedIndex(){
        this.index = new HashMap<>();
    }

    public void addDocument(String documentId,List<String> tokens){
        for(String token : tokens){
            index.putIfAbsent(token,new HashMap<>());
            Map<String,Integer>docMap = index.get(token);
            docMap.put(documentId, docMap.getOrDefault(documentId, 0) + 1);
        }
    }

    public Map<String,Integer> search(String term){
        return index.getOrDefault(term,new HashMap<>());
    }

    public void printIndex(){
        for (Map.Entry<String,Map<String,Integer>>entry:index.entrySet()){
            System.out.println(entry.getKey()+"->"+entry.getValue());
        }
    }

}