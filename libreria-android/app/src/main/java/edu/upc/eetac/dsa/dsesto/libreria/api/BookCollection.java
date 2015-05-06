package edu.upc.eetac.dsa.dsesto.libreria.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookCollection {
    private List<Book> books;
    private int firstBook;
    private int lastBook;
    private Map<String, Link> links = new HashMap<String, Link>();

    public BookCollection() {
        super();
        books = new ArrayList<Book>();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int getFirstBook() {
        return firstBook;
    }

    public void setFirstBook(int firstBook) {
        this.firstBook = firstBook;
    }

    public int getLastBook() {
        return lastBook;
    }

    public void setLastBook(int lastBook) {
        this.lastBook = lastBook;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }
}