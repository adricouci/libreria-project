package edu.upc.eetac.dsa.dsesto.libreria.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import edu.upc.eetac.dsa.dsesto.libreria.api.MediaType;
import edu.upc.eetac.dsa.dsesto.libreria.api.ReviewResource;

public class ReviewCollection {
	@InjectLinks({
			@InjectLink(resource = ReviewResource.class, style = Style.ABSOLUTE, rel = "create-review", title = "Crear review", type = MediaType.LIBRERIA_API_REVIEW),
			@InjectLink(value = "/reviews?before={before}", style = Style.ABSOLUTE, rel = "previous", title = "Libros anteriores", type = MediaType.LIBRERIA_API_REVIEW_COLLECTION, bindings = { @Binding(name = "before", value = "${instance.firstReview}") }),
			@InjectLink(value = "/reviews?after={after}", style = Style.ABSOLUTE, rel = "following", title = "Libros posteriores", type = MediaType.LIBRERIA_API_REVIEW_COLLECTION, bindings = { @Binding(name = "after", value = "${instance.lastReview}") }) })
	private List<Link> links;
	private int firstReview;
	private int lastReview;

	private List<Review> reviews;
	
	public ReviewCollection() {
		super();
		reviews = new ArrayList<>();
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
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

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public void addReview(Review review) {
		reviews.add(review);
	}
}
