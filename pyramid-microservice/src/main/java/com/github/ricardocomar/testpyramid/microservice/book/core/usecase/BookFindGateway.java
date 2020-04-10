package com.github.ricardocomar.testpyramid.microservice.book.core.usecase;

import java.util.List;

import com.github.ricardocomar.testpyramid.microservice.book.core.entity.Book;

public interface BookFindGateway {

	List<Book> find(int first, int maxResult);

	Book find(long id);
}
