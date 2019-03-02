package com.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	public Optional<Book> findByBookName(String bookName);

	public List<Book> findByBookNameContainingIgnoreCase(String bookName);
	public Long removeByBookName(String bookName);
}
