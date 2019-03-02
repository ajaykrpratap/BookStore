package com.store.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.store.model.Book;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {
	@Autowired
	private TestEntityManager testEntiryManager;
	@Autowired
	private BookRepository bookRepository;

	@Test
	public void testSaveBook() {
		Book book = getBook();
		Book saveInDb = testEntiryManager.persist(book);
		Optional<Book> getFromDb = bookRepository.findById(saveInDb.getId());
		assertThat(getFromDb.get()).isEqualTo(saveInDb);
	}

	@Test
	public void testFindBook() {
		Book book = getBook();
		Book saveInDb = testEntiryManager.persist(book);
		Optional<Book> getFromDb = bookRepository.findByBookName(saveInDb.getBookName());
		assertThat(getFromDb.get()).isEqualTo(saveInDb);
	}

	@Test
	public void testFindBookFail() {
		Book book = getBook();
		Book saveInDb = testEntiryManager.persist(book);
		Optional<Book> getFromDb = bookRepository.findByBookName("C");
		assertThat(getFromDb.get()).isEqualTo(saveInDb);
	}

	@Test
	public void testDeleteBook() {
		Book book = getBook();
		Book saveInDb = testEntiryManager.persist(book);
		Long delCount = bookRepository.removeByBookName(saveInDb.getBookName());
		assertEquals(new Long(1), delCount);
	}

	@Test
	public void testDeleteBookFail() {
		Book book = getBook();
		testEntiryManager.persist(book);
		Long delCount = bookRepository.removeByBookName("C");
		assertEquals(new Long(1), delCount);
	}

	private Book getBook() {
		Book book = new Book();
		book.setBookName("JAVA");
		book.setDescription("Advance Java");
		return book;
	}

}
