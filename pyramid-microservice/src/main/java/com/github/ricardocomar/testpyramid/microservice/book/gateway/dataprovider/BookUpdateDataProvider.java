package com.github.ricardocomar.testpyramid.microservice.book.gateway.dataprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.ricardocomar.testpyramid.microservice.book.gateway.dataprovider.mapper.BookEntityMapper;
import com.github.ricardocomar.testpyramid.microservice.book.core.entity.Book;
import com.github.ricardocomar.testpyramid.microservice.book.gateway.dataprovider.repository.BookRepository;
import com.github.ricardocomar.testpyramid.microservice.book.gateway.dataprovider.repository.entity.BookEntity;
import com.github.ricardocomar.testpyramid.microservice.book.core.usecase.BookUpdateGateway;


@Component
@Transactional
public class BookUpdateDataProvider implements BookUpdateGateway {

	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public Book update(Book book) {
		BookEntity entity = BookEntityMapper.from(book);
		BookEntity saved = bookRepository.save(entity);
		return BookEntityMapper.from(saved);
	}

}
