package com.github.ricardocomar.testpyramid.microservice.book.gateway.dataprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.ricardocomar.testpyramid.microservice.book.gateway.dataprovider.repository.BookRepository;
import com.github.ricardocomar.testpyramid.microservice.book.core.usecase.BookDeleteGateway;

@Component
@Transactional
public class BookDeleteDataProvider implements BookDeleteGateway {

	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public void delete(long id) {
		bookRepository.deleteById(id);
	}

}
