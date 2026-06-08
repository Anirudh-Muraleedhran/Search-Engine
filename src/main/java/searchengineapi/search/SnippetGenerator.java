package searchengineapi.search;

public class SnippetGenerator {
    private static final int WINDOW = 100;

    public static String generateSnippet(String text,String query)
    {
        if(text == null || text.isEmpty())
        {
            return "";
        }

        String lowerText = text.toLowerCase();
        String lowerQuery = query.toLowerCase();

        int index = lowerText.indexOf(lowerQuery);

        if(index == -1)
        {
            return text.length() > 200? text.substring(0, 200): text;
        }

        int start = Math.max(0,index-WINDOW);
        int end = Math.min(text.length(),index + lowerQuery.length() + WINDOW);

        return text.substring(start,end);
    }
}
