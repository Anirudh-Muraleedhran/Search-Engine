package searchengineapi.service;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import searchengineapi.crawler.WebCrawler;
import searchengineapi.indexing.InvertedIndex;
import searchengineapi.model.DocumentMetadata;
import searchengineapi.search.SearchEngine;
import searchengineapi.search.SnippetGenerator;
import searchengineapi.dto.SearchResultDTO;
import searchengineapi.service.DocumentMetadataStore;
import searchengineapi.model.DocumentMetadata;

import java.util.*;

@Service
public class SearchService {
    private WebCrawler crawler;
    private InvertedIndex invertedIndex;
    private SearchEngine searchEngine;
    private DocumentMetadataStore metadataStore;


    @PostConstruct
    public void init()
    {
        System.out.println("Initializing Search Engine");

        invertedIndex = new InvertedIndex();
        metadataStore = new DocumentMetadataStore();

        crawler = new WebCrawler(50,invertedIndex,metadataStore);
        crawler.startCrawling("https://en.wikipedia.org/wiki/Search_engine");
        searchEngine = new SearchEngine(invertedIndex);

        System.out.println("Search Engine is Ready");
        System.out.println("Indexed "+ invertedIndex.getTotalDocuments()+"documents");
    }

    public List<SearchResultDTO> search(String query)
    {
        Map<String, Double> scores = searchEngine.search(query);

        List<Map.Entry<String, Double>> ranked =
                searchEngine.rankResults(scores);

        List<SearchResultDTO> response = new ArrayList<>();

        for (Map.Entry<String, Double> entry : ranked)
        {
            String url = entry.getKey();
            double score = entry.getValue();

            DocumentMetadata metadata = metadataStore.getMetadata(url);
            String querySnippet = SnippetGenerator.generateSnippet(metadata.getFullText(), query);
            if (metadata != null)
            {
                response.add(new SearchResultDTO(url,metadata.getTitle(),querySnippet,score));
            }
        }

        return response;
    }

    public List<SearchResultDTO> phaseSearch(String query)
    {
        Map<String,Double> scores = searchEngine.phraseSearch(query);
        List<Map.Entry<String,Double>> ranked = searchEngine.rankResults(scores);

        List<SearchResultDTO> results = new ArrayList<>();

        for(Map.Entry<String,Double> entry : ranked)
        {
            String url = entry.getKey();
            double score = entry.getValue();

            DocumentMetadata metadata = metadataStore.getMetadata(url);
            String querySnippet = SnippetGenerator.generateSnippet(metadata.getFullText(), query);
            if(metadata != null)
            {
                results.add(new SearchResultDTO(url,metadata.getTitle(),querySnippet,score));
            }
        }

        return results;
    }
}
