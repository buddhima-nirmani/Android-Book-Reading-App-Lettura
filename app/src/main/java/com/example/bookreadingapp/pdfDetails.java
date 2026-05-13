package com.example.bookreadingapp;

public class pdfDetails {
    String uid, id, title, author, url;
    long timestamp;

    //empty constructor for firebase
    public pdfDetails() {

    }

    //constructor with parameters

    public pdfDetails(String uid, String id, String title, String author, String url, long timestamp) {
        this.uid = uid;
        this.id = id;
        this.title = title;
        this.author = author;
        this.url = url;
        this.timestamp = timestamp;
    }

    //getters & setters


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
