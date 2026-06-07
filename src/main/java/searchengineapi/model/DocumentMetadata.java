package searchengineapi.model;

public class DocumentMetadata {
    private String url;
    private String title;
    private String snippet;

    public DocumentMetadata(){}

    public DocumentMetadata(String url,String title,String snippet)
    {
        this.title = title;
        this.url = url;
        this.snippet = snippet;
    }

    public String getUrl()
    {
        return url;
    }

    public String getTitle()
    {
        return title;
    }

    public String getSnippet()
    {
        return snippet;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setSnippet(String snippet)
    {
        this.snippet = snippet;
    }
}
