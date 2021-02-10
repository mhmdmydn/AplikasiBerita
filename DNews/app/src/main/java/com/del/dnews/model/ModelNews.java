package com.del.dnews.model;
import java.util.Comparator;

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
    
    public static Comparator<ModelNews> sortByTitle = new Comparator<ModelNews>(){

        @Override
        public int compare(ModelNews object1, ModelNews object2) {
            
            return - Integer.valueOf(object1.title).compareTo(Integer.valueOf(object2.title));
        }
    };
    
    public static Comparator<ModelNews> sortByAuthor = new Comparator<ModelNews>(){

        @Override
        public int compare(ModelNews object1, ModelNews object2) {
            return - Integer.valueOf(object1.author).compareTo(Integer.valueOf(object2.author));
        }
    };
    
    
    public static Comparator<ModelNews> sortByDate = new Comparator<ModelNews>(){

        @Override
        public int compare(ModelNews object1, ModelNews object2) {
            return - Integer.valueOf(object1.publishedAt).compareTo(Integer.valueOf(object2.publishedAt));
        }
    };
}
