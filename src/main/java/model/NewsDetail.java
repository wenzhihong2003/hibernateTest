package model;

/**
 * User: wenzhihong
 * Date: 13-11-22
 * Time: 下午5:50
 */
public class NewsDetail {
    private Long id;

    private String content;

    private String subContent;

    private News news;

    public NewsDetail() {
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubContent() {
        return subContent;
    }

    public void setSubContent(String subContent) {
        this.subContent = subContent;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }
}
