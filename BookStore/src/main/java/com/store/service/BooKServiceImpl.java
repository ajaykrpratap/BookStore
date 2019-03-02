package com.store.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.store.controller.BookController;
import com.store.exception.BookUniqueConstraintViolationException;
import com.store.model.Book;
import com.store.repository.BookRepository;

@Service
public class BooKServiceImpl implements IBookService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BooKServiceImpl.class);

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private MessageSource messageSource;

	@Override
	public List<Book> getAllBooks(String bookName) {
		List<Book> books;
		if (!StringUtils.isEmpty(bookName)) {
			books = bookRepository.findByBookNameContainingIgnoreCase(bookName);
		} else {
			books = bookRepository.findAll();
		}
		return books;
	}

	@Override
	public Optional<Book> getBookDetails(String bookName) {
		Optional<Book> book = bookRepository.findByBookName(bookName);
		return book;
	}

	@Transactional
	@Override
	public Book saveOrUpdate(Book book) {
		LOGGER.debug("Saving book details for" + book.getBookName());
		try {
			book = bookRepository.save(book);
			LOGGER.debug("Successfully saved book details for ", book.getBookName());
		} catch (DataIntegrityViolationException ex) {
			LOGGER.error("Couldn't saved book " + book.getBookName() + " beacuse this book is already exist");
			throw new BookUniqueConstraintViolationException(book.getBookName() + " "
					+ messageSource.getMessage("book.unique.name", null, LocaleContextHolder.getLocale()));
		}
		return book;

	}

	@Transactional
	@Override
	public Long deleteBook(String bookName) {
		LOGGER.debug("Deleting book for " + bookName);
		return bookRepository.removeByBookName(bookName);
	}

}
