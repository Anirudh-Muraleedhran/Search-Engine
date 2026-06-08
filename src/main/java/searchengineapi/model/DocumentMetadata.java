package searchengineapi.model;

public class DocumentMetadata
{
    private String url;
    private String title;
    private String snippet;
    private String fullText;

    public DocumentMetadata()
    {
    }

    public DocumentMetadata(
            String url,
            String title,
            String snippet,
            String fullText)
    {
        this.url = url;
        this.title = title;
        this.snippet = snippet;
        this.fullText = fullText;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getSnippet()
    {
        return snippet;
    }

    public void setSnippet(String snippet)
    {
        this.snippet = snippet;
    }

    public String getFullText()
    {
        return fullText;
    }

    public void setFullText(String fullText)
    {
        this.fullText = fullText;
    }
}