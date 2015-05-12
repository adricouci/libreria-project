package edu.upc.eetac.dsa.acouceiro.libreria.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewCollection {
    private List<Review> reviews;
    private int firstReview;
    private int lastReview;
    private Map<String, Link> links = new HashMap<String, Link>();

    public ReviewCollection() {
        super();
        reviews = new ArrayList<Review>();
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public int getFirstReview() {
        return firstReview;
    }

    public void setFirstReview(int firstReview) {
        this.firstReview = firstReview;
    }

    public int getLastReview() {
        return lastReview;
    }

    public void setLastReview(int lastReview) {
        this.lastReview = lastReview;
    }
}