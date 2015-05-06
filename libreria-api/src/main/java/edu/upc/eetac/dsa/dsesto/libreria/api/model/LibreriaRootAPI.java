package edu.upc.eetac.dsa.dsesto.libreria.api.model;

import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.dsesto.libreria.api.BookResource;
import edu.upc.eetac.dsa.dsesto.libreria.api.ReviewResource;
import edu.upc.eetac.dsa.dsesto.libreria.api.LibreriaRootAPIResource;
import edu.upc.eetac.dsa.dsesto.libreria.api.MediaType;

//Es un POJO (no hereda de nada, atributos privados, getters y setters)
public class LibreriaRootAPI {
	@InjectLinks({
			@InjectLink(resource = LibreriaRootAPIResource.class, style = Style.ABSOLUTE, rel = "self bookmark home", title = "Libreria Root API", method = "getRootAPI"),
			@InjectLink(resource = BookResource.class, style = Style.ABSOLUTE, rel = "books", title = "Lista de libros", type = MediaType.LIBRERIA_API_BOOK_COLLECTION),
			@InjectLink(resource = ReviewResource.class, style = Style.ABSOLUTE, rel = "post-reviews", title = "Create review", type = MediaType.LIBRERIA_API_REVIEW),
			@InjectLink(resource = BookResource.class, style = Style.ABSOLUTE, rel = "create-books", title = "Crear libro", type = MediaType.LIBRERIA_API_BOOK) })
	private List<Link> links;

	// Style.ABSOLUTE -> vamos a ver la URI absoluta
	// (http://localhost:[p]/beeter-api/) la última barra viene de que el método
	// BeeterRootAPIResource tiene @Path("/")

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}
}