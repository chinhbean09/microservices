package com.chinhbean.bookservice.command.api.events;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.chinhbean.bookservice.command.api.data.Book;
import com.chinhbean.bookservice.command.api.data.BookRepository;

@Component
public class BookEventsHandler {

	@Autowired
	private BookRepository bookRepository;
	
	@EventHandler
    public void on(BookCreatedEvent event) {
       Book book = new Book();
        BeanUtils.copyProperties(event,book);
        bookRepository.save(book);
    }

}
