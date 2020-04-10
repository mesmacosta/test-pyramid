package com.github.ricardocomar.testpyramid.microservice.book.gateway.dataprovider.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.github.ricardocomar.testpyramid.microservice.book.gateway.dataprovider.repository.entity.BookEntity;

public interface BookRepository extends PagingAndSortingRepository<BookEntity, Long> {

	List<BookEntity> findByName(String name);
	
}
