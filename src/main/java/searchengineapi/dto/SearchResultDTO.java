package searchengineapi.dto;

public class SearchResultDTO
{
    private String url;
    private double score;

    public SearchResultDTO(){}

    public SearchResultDTO(String url,double score)
    {
        this.url = url;
        this.score = score;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}