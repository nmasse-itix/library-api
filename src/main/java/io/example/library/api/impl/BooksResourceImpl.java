package io.example.library.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import io.example.library.api.BooksResource;
import io.example.library.api.beans.Book;

@ApplicationScoped
public class BooksResourceImpl implements BooksResource {
	
	private Map<String, Book> bookDB = new HashMap<>();
	
	public BooksResourceImpl() {
	}

	@Override
	public List<Book> getbooks() {
		List<Book> rval = new ArrayList<>();
		rval.addAll(this.bookDB.values());
		return rval;
	}

	@Override
	public void createBook(Book data) {
		this.bookDB.put(data.getDdsn(), data);
	}

	@Override
	public Book getBook(String bookId) {
		return this.bookDB.get(bookId);
	}

	@Override
	public void updateBook(String bookId, Book data) {
		this.bookDB.put(data.getDdsn(), data);
	}

	@Override
	public void deleteBook(String bookId) {
		this.bookDB.remove(bookId);
	}

}
