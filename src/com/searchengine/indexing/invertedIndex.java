package com.searchengine.indexing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InvertedIndex{
    private Map<String,Set<String>>index;
    public InvertedIndex(){
        this.index = new HashMap<>();
    }

    public void addDocument(String documentId,List<String> tokens){
        for(String token : tokens){
            index.putIfAbsent(token,new HashSet<>());
            index.get(token).add(documentId);
        }
    }

    public Set<String> search(String term){
        return index.getOrDefault(term,new HashSet<>());
    }

    public void printIndex(){
        for (Map.Entry<String,Set<String>>entry:index.entrySet()){
            System.out.println(entry.getKey()+"->"+entry.getValue());
        }
    }

}