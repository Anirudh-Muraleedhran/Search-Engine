package searchengineapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import searchengineapi.dto.SearchResultDTO;
import searchengineapi.service.SearchService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class SearchController {

    private final SearchService searchService;

    SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/")
    public String home() {
        return "Search Engine API Running";
    }

    @GetMapping("/search")
    public List<SearchResultDTO> search(@RequestParam String q) {
        return searchService.search(q);
    }
    
    @GetMapping("/phase-search")
    public List<SearchResultDTO> phaseSearch(@RequestParam String q) {
        return searchService.phaseSearch(q);
    }
    
}