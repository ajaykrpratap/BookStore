package com.store.service;

import java.util.List;
import java.util.Optional;

import com.store.model.Book;

public interface IBookService {
	public List<Book> getAllBooks(String bookName);

	public Optional<Book> getBookDetails(String bookName);

	public Book saveOrUpdate(Book book);

	public Long deleteBook(String bookName);

}
