package com.chinhbean.bookservice.query.api.projection;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinhbean.bookservice.command.api.data.Book;
import com.chinhbean.bookservice.command.api.data.BookRepository;
import com.chinhbean.bookservice.query.api.model.BookResponseModel;
import com.chinhbean.bookservice.query.api.queries.GetAllBooksQuery;
import com.chinhbean.bookservice.query.api.queries.GetBooksQuery;


@Component
public class BookProjection {

	@Autowired
	private BookRepository bookRepository;
	
	 @QueryHandler
	    public BookResponseModel handle(GetBooksQuery getBooksQuery) {
		 BookResponseModel model = new BookResponseModel();
		 Book book = bookRepository.getById(getBooksQuery.getBookId());
	      BeanUtils.copyProperties(book, model);

	        return model;
	    }
	 @QueryHandler List<BookResponseModel> handle(GetAllBooksQuery getAllBooksQuery){
		 List<Book> listEntity = bookRepository.findAll();
		 List<BookResponseModel> listbook = new ArrayList<>();
		 listEntity.forEach(s -> {
			 BookResponseModel model = new BookResponseModel();
			 BeanUtils.copyProperties(s, model);
			 listbook.add(model);
		 });
		 return listbook;
	 }
}
