package edu.upc.eetac.dsa.dsesto.libreria.api;

import javax.sql.DataSource;
import javax.ws.rs.Path;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.mysql.jdbc.Statement;

import edu.upc.eetac.dsa.dsesto.libreria.api.model.Author;
import edu.upc.eetac.dsa.dsesto.libreria.api.model.Book;
import edu.upc.eetac.dsa.dsesto.libreria.api.model.BookCollection;

@Path("/authors")
public class AuthorResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();

	private String GET_AUTHOR_BY_NAME = "select * from authors where name = ?";
	private String INSERT_AUTHOR_QUERY = "insert into authors values (?)";
	private String UPDATE_AUTHOR_QUERY = "update authors set name = ? where name = ?";
	private String DELETE_AUTHOR_QUERY = "delete from authors where name=?";
	
	// Crear ficha de autor
	@POST
	@Consumes(MediaType.LIBRERIA_API_AUTHOR)
	@Produces(MediaType.LIBRERIA_API_AUTHOR)
	public Author createAuthor(Author author) {
		validateAuthor(author);

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(INSERT_AUTHOR_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, author.getName());
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				String name = rs.getString(1);

				author = getAuthorFromDatabase(name);
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		return author;
	}

	// Modificar ficha de autor
	@PUT
	@Path("/{authorname}")
	@Consumes(MediaType.LIBRERIA_API_AUTHOR)
	@Produces(MediaType.LIBRERIA_API_AUTHOR)
	public Author updateAuthor(@PathParam("authorname") String authorname, Author author) {
		validateAuthor(author);

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(UPDATE_AUTHOR_QUERY);
			stmt.setString(1, author.getName());
			stmt.setString(2, authorname);
			
			int rows = stmt.executeUpdate();

			if (rows == 1)
				author = getAuthorFromDatabase(author.getName());
			else {
				throw new NotFoundException("There's no author with name="
						+ authorname);
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		return author;
	}
	
	// Borrar un autor
	@DELETE
	@Path("/{name}")
	public void deleteAuthor(@PathParam("name") String name) {
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_AUTHOR_QUERY);
			stmt.setString(1, name);

			int rows = stmt.executeUpdate();
			if (rows == 0)
				throw new NotFoundException("There's no author with name="
						+ name);
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	// Método para validar que los datos del autor son correctos
	private void validateAuthor(Author author) {
		if (author.getName() == null)
			throw new BadRequestException("Name can't be null");
	}

	// Método para recuperar autor de la BD
	private Author getAuthorFromDatabase(String name) {
		Author author = new Author();

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_AUTHOR_BY_NAME);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				author.setName(rs.getString("name"));
			} else {
				throw new NotFoundException("There's no author with name="
						+ name);
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		return author;
	}
}