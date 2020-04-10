package com.github.ricardocomar.testpyramid.microservice.book.gateway.dataprovider.mapper;

import com.github.ricardocomar.testpyramid.microservice.book.core.entity.Book;
import com.github.ricardocomar.testpyramid.microservice.book.gateway.dataprovider.repository.entity.BookEntity;

public class BookEntityMapper {
	
	public static BookEntity from(Book book) {
		return (book == null) ? null : 
				BookEntity.builder()
					.id(book.getId())
					.name(book.getName())
					.writter(book.getWritter())
					.price(book.getPrice())
					.build();
	}

	public static Book from(BookEntity entity) {
		return (entity == null) ? null : 
				Book.builder()
					.id(entity.getId())
					.name(entity.getName())
					.writter(entity.getWritter())
					.price(entity.getPrice())
					.build();
	}

}
