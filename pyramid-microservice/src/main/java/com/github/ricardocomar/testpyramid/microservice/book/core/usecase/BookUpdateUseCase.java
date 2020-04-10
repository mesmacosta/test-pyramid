package com.github.ricardocomar.testpyramid.microservice.book.core.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.ricardocomar.testpyramid.microservice.book.core.entity.Book;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BookUpdateUseCase {

	@Autowired
	private BookUpdateGateway updateGateway;

	public Book update(Book book) {
		return updateGateway.update(book);
	}

}
