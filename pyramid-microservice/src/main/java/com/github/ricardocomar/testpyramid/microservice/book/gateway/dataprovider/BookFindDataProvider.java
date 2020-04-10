package com.github.ricardocomar.testpyramid.microservice.book.gateway.dataprovider;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.ricardocomar.testpyramid.microservice.book.gateway.dataprovider.mapper.BookEntityMapper;
import com.github.ricardocomar.testpyramid.microservice.book.core.entity.Book;
import com.github.ricardocomar.testpyramid.microservice.book.gateway.dataprovider.repository.BookRepository;
import com.github.ricardocomar.testpyramid.microservice.book.gateway.dataprovider.repository.entity.BookEntity;
import com.github.ricardocomar.testpyramid.microservice.book.core.usecase.BookFindGateway;

@Repository
@Transactional
public class BookFindDataProvider implements BookFindGateway {

	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public List<Book> find(int first, int maxResult) {
		return bookRepository.findAll(PageRequest.of(first, maxResult))
				.stream().map(entity -> BookEntityMapper.from(entity))
				.collect(Collectors.toList());
	}

	@Override
	public Book find(long id) {
		Optional<BookEntity> optional = bookRepository.findById(id);
		return BookEntityMapper.from(optional.orElse(null));
	}

}
