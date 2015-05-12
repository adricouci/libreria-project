package edu.upc.eetac.dsa.acouceiro.libreria.api;

import java.util.HashMap;
import java.util.Map;

public class Book {
    private String title;
    private int bookid;
    private String author;
    private String language;
    private String edition;
    private String editonDate;
    private String printingDate;
    private String publisher;
    private String eTag;
    private Map<String, Link> links = new HashMap<String, Link>();

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getEditonDate() {
        return editonDate;
    }

    public void setEditonDate(String editonDate) {
        this.editonDate = editonDate;
    }

    public String getPrintingDate() {
        return printingDate;
    }

    public void setPrintingDate(String printingDate) {
        this.printingDate = printingDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getETag() {
        return eTag;
    }

    public void setETag(String eTag) {
        this.eTag = eTag;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}