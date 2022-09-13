package com.gokisoft.example.models;

public class Book {
    String bookName, authorName, nxb;
    float price;

    public Book() {
    }

    public Book(String bookName, String authorName, String nxb, float price) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.nxb = nxb;
        this.price = price;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getNxb() {
        return nxb;
    }

    public void setNxb(String nxb) {
        this.nxb = nxb;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
