package com.searchengine.crawler;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkExtractor{

    private static final String URL_PATTERN = "href\\s*=\\s*\"(http[s]?://[^\"#]+)\"";

    public Set<String> extractLinks(String html)
    {
        Set<String> links  = new HashSet<>();
        Pattern pattern = Pattern.complie(URL_PATTERN);
        Matcher matcher = Pattern.matcher(html);

        while(matcher.find())
        {
            links.add(matcher.group(1));
        }

        return links;
    }
}