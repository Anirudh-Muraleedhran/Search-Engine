package com.searchengine.indexing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class Indexer{
    private InvertedIndex invertedIndex;
    private Tokenizer tokenizer;

    public Indexer(InvertedIndex invertedIndex,Tokenizer tokenizer){
        this.invertedIndex=invertedIndex;
        this.tokenizer=tokenizer;
    }

    public void indexDocument(String directoryPath){
        System.out.println("Starting indexing process for directory: " + directoryPath);
        //to traverse file securely
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))){
            paths.filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".txt"))
                .forEach(this::processFile);

        }

        catch(IOException e){
            System.err.println("Error reading the document directory: " + e.getMessage());
        }
    }

    private void processFile(Path filePath){
        try{
            String content = new String(Files.readAllBytes(filePath));
            List<String> tokens = tokenizer.tokenize(content);
            String documentId = filePath.getFileName().toString();
            invertedIndex.addDocument(documentId,tokens);
            System.out.println("Successfully indexed :"+ documentId +"(" + tokens.size() + "words)");
            
        }
        catch(IOException e){
            System.err.println("Failed to read file " +filePath.getFileName() + ": " + e.getMessage());
        }
    }
}
