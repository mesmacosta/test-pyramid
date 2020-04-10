package com.github.ricardocomar.testpyramid.microservice.book.core.usecase;

import com.github.ricardocomar.testpyramid.microservice.book.core.entity.Book;


public interface BookUpdateGateway {

	Book update(Book book);

}
