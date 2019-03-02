package com.store.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.store.BookStoreApplication;
import com.store.exception.BookNotFoundException;
import com.store.model.Book;
import com.store.model.Response;
import com.store.service.IBookService;

@RestController
@RequestMapping(value = "/api/book")
public class BookController {

	public static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
	@Autowired
	private IBookService bookService;
	@Autowired
	private MessageSource messageSource;

	@GetMapping("/get-all-books")
	public ResponseEntity<List<Book>> getAllBooks(@RequestParam(value = "bookName", required = false) String bookName) {
		LOGGER.info("Fetching book for " + bookName);
		List<Book> bookList = bookService.getAllBooks(bookName);
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<List<Book>>(bookList, httpHeaders, HttpStatus.OK);
	}

	@PostMapping("/save")
	public ResponseEntity<Response> saveBook(@Valid @RequestBody Book book) {
		LOGGER.info("Start saving book detail");
		bookService.saveOrUpdate(book);
		LOGGER.info("Successfully Saved book details");
		Response response = new Response(new Date(), "Success",
				messageSource.getMessage("book.save.success", null, LocaleContextHolder.getLocale()),
				HttpStatus.CREATED.value());
		return new ResponseEntity<Response>(response, HttpStatus.CREATED);
	}

	@GetMapping("/get-book-detail")
	public ResponseEntity<Book> getPerson(@RequestParam(value = "bookName", required = true) String bookName) {
		Optional<Book> book = bookService.getBookDetails(bookName);
		if (!book.isPresent()) {
			throw new BookNotFoundException(
					bookName + " " + messageSource.getMessage("book.not.found", null, LocaleContextHolder.getLocale()));
		}
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<Book>(book.get(), httpHeaders, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Response> deletePerson(@RequestParam("bookName") String bookName) {
		Long delCount = bookService.deleteBook(bookName);
		if (delCount == 0) {
			throw new BookNotFoundException(bookName + " "
					+ messageSource.getMessage("book.not.found.for.del", null, LocaleContextHolder.getLocale()));
		}
		Response response = new Response(new Date(), "Success",
				bookName + " " + messageSource.getMessage("book.delete.success", null, LocaleContextHolder.getLocale()),
				HttpStatus.OK.value());
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
