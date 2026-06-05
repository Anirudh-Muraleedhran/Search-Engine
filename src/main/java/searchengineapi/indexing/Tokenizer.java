package searchengineapi.indexing;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer{
    public List<String> tokenize(String text){
        List<String> tokens = new  ArrayList<>();
        if (text==null || text.trim().isEmpty()){
            return tokens;
        }

        String lowerCaseText = text.toLowerCase();
        String cleanText=lowerCaseText.replaceAll("[^a-z0-9\\s]","");
        String[] words=cleanText.split("\\s+");

        for (String word : words){
            if (!word.isEmpty()){
                tokens.add(word);
            }
        }
        return tokens;
    }

}