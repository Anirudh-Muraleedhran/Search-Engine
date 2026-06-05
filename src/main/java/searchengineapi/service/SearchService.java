package searchengineapi.service;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import searchengineapi.crawler.WebCrawler;
import searchengineapi.indexing.InvertedIndex;
import searchengineapi.search.SearchEngine;
import searchengineapi.dto.SearchResultDTO;

import java.util.*;

@Service
public class SearchService {
    private WebCrawler crawler;
    private InvertedIndex invertedIndex;
    private SearchEngine searchEngine;

    @PostConstruct
    public void init()
    {
        System.out.println("Initializing Search Engine");

        invertedIndex = new InvertedIndex();
        crawler = new WebCrawler(50,invertedIndex);
        crawler.startCrawling("https://en.wikipedia.org/wiki/Search_engine");
        searchEngine = new SearchEngine(invertedIndex);

        System.out.println("Search Engine is Ready");
        System.out.println("Indexed "+ invertedIndex.getTotalDocuments()+"documents");
    }

    public List<SearchResultDTO> search(String query)
    {
        Map<String,Double> results = searchEngine.search(query);
        List<SearchResultDTO> response  = new ArrayList<>();

        for(Map.Entry<String,Double>entry : results.entrySet() )
        {
            response.add(new SearchResultDTO(entry.getKey(),entry.getValue()));
        }

        return response;
    }

    public List<SearchResultDTO> phaseSearch(String query)
    {
        Map<String,Double> scores = searchEngine.phraseSearch(query);
        List<Map.Entry<String,Double>> ranked = searchEngine.rankResults(scores);

        List<SearchResultDTO> results = new ArrayList<>();

        for(Map.Entry<String,Double>entry : ranked)
        {
            results.add(new SearchResultDTO(entry.getKey(),entry.getValue()));
        }

        return results;
    }
}
