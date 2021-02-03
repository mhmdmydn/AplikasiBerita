package com.del.dnews.model;

public class ModelNews {

    private String title;
    private String publishedAt;
    private String url;
    private String urlToImage;
    private String author;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }
    
    public void setAuthorName(String author){
        this.author = author;
    }
    
    public String getAuthor(){
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }
}
