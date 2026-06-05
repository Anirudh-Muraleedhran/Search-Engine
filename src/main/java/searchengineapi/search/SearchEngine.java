package searchengineapi.search;

import searchengineapi.indexing.InvertedIndex;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashSet;

public class SearchEngine
{
    private InvertedIndex invertedIndex;

    public SearchEngine(InvertedIndex invertedIndex)
    {
        this.invertedIndex = invertedIndex;
    }

    public Map<String, Double> search(String query)
    {
        String[] terms = query.toLowerCase().split("\\s+");

        Map<String, Double> scores = new HashMap<>();

        int totalDocs = invertedIndex.getTotalDocuments();

        for (String term : terms)
        {
            Map<String, List<Integer>> docs =invertedIndex.search(term);
            int df =invertedIndex.getDocumentFrequency(term);

            if (df == 0)
            {
                continue;
            }

            double idf =Math.log((double) totalDocs / df);

            for (Map.Entry<String, List<Integer>> entry :docs.entrySet())
            {
                String doc = entry.getKey();
                int tf = entry.getValue().size();
                double tfidf = tf * idf;

                scores.put(doc,scores.getOrDefault(doc, 0.0)+ tfidf);
            }
        }
        return scores;
    }

    public List<Map.Entry<String, Double>> rankResults(Map<String, Double> scores)
    {
        List<Map.Entry<String, Double>> list = new ArrayList<>(scores.entrySet());

        list.sort((a, b) ->Double.compare(b.getValue(),a.getValue()));

        return list;
    }

    public Map<String,Double> phraseSearch(String phrase)
    {
        String[] terms = phrase.toLowerCase().split("\\s+");
        Map<String,Integer> phraseFrequencies  = new HashMap<>();

        if (terms.length == 0)
        {
            return new HashMap<>() ;
        }

        Map<String, List<Integer>> firstDocs =invertedIndex.search(terms[0]);

        for (String docId : firstDocs.keySet())
        {
            List<Integer> candidates =new ArrayList<>(invertedIndex.getPositions(terms[0],docId));

            for (int i = 1; i < terms.length; i++)
            {
                List<Integer> nextPositions =invertedIndex.getPositions(terms[i],docId);

                Set<Integer> nextSet =new HashSet<>(nextPositions);
                        
                List<Integer> newCandidates =new ArrayList<>();

                for (int pos : candidates)
                {
                    if (nextSet.contains(pos + 1))
                    {
                        newCandidates.add(pos + 1);
                    }
                }

                candidates = newCandidates;

                if (candidates.isEmpty())
                {
                    break;
                }
            }

            if (!candidates.isEmpty())
            {
                int phrasefrequency = candidates.size();
                phraseFrequencies .put(docId,phrasefrequency);
            }
        }
        int phraseDF = phraseFrequencies.size();
        int totalDocs = invertedIndex.getTotalDocuments();
        if (phraseDF == 0)
        {
            return new HashMap<>();
        }
        double phraseIDF = Math.log((double) totalDocs / phraseDF);

        Map<String, Double> scores = new HashMap<>();

        for(Map.Entry<String,Integer> entry : phraseFrequencies.entrySet())
        {
            String docId = entry.getKey();
            int tf = entry.getValue();
            double score = (1 + Math.log(tf)) * phraseIDF;
            scores.put(docId,score);
        }
        return scores ;
    }
}