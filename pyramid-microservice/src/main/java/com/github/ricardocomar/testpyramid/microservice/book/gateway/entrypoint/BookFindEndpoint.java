package com.github.ricardocomar.testpyramid.microservice.book.gateway.entrypoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.ricardocomar.testpyramid.microservice.book.core.entity.Book;
import com.github.ricardocomar.testpyramid.microservice.book.core.usecase.BookFindUseCase;

@RestController
@RequestMapping(value = "/api/book")
public class BookFindEndpoint {

	@Autowired
	private BookFindUseCase bookAction;

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<Book> find(@PathVariable long id) {
		Book found = bookAction.find(id);
		if (found == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(found);
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Book> find(@RequestParam("start") Integer first,
			@RequestParam("maxResult") Integer maxResult) {
		return bookAction.find(first, maxResult);
	}

}
