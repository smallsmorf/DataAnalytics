package com.models;

public class Post {
    private int id;
    private String content;
    private String author;
    private int numLikes;
    private int numShares;
    private String dateTime;

    public Post(int id, String content, String author, int numLikes, int numShares, String dateTime) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.numLikes = numLikes;
        this.numShares = numShares;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public int getNumShares() {
        return numShares;
    }

    public String getDateTime() {
        return dateTime;
    }

}
