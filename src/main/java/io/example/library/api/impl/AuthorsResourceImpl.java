package io.example.library.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import io.example.library.api.AuthorsResource;
import io.example.library.api.beans.Author;

@ApplicationScoped
public class AuthorsResourceImpl implements AuthorsResource {
	
	private Map<String, Author> authorDB = new HashMap<>();
	
	public AuthorsResourceImpl() {
		Author author = new Author();
		author.setId("poe");
		author.setDob("1809-01-19");
		author.setName("Edgar Allan Poe");
		this.authorDB.put("", author);
	}

	@Override
	public List<Author> getauthors() {
		List<Author> rval = new ArrayList<>();
		rval.addAll(this.authorDB.values());
		return rval;
	}

	@Override
	public void createAuthor(Author data) {
		this.authorDB.put(data.getId(), data);
	}

	@Override
	public Author getAuthor(String authorId) {
		return this.authorDB.get(authorId);
	}

	@Override
	public void updateAuthor(String authorId, Author data) {
		this.authorDB.put(authorId, data);
	}

	@Override
	public void deleteAuthor(String authorId) {
		this.authorDB.remove(authorId);
	}

}
