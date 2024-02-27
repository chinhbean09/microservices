package com.chinhbean.bookservice.command.api.aggregate;

import com.chinhbean.bookservice.command.api.command.DeleteBookCommand;
import com.chinhbean.bookservice.command.api.events.BookDeletedEvent;
import com.chinhbean.bookservice.command.api.events.BookUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.chinhbean.bookservice.command.api.command.CreateBookCommand;
import com.chinhbean.bookservice.command.api.events.BookCreatedEvent;

@Aggregate
public class BookAggregate {

	@AggregateIdentifier
	private String bookId;
	private String name;
	private String author;
	private Boolean isReady;
	
	 public BookAggregate() {
	    }
	 
	@CommandHandler
    public BookAggregate(CreateBookCommand createBookCommand) {
        
        BookCreatedEvent bookCreatedEvent
                = new BookCreatedEvent();
        BeanUtils.copyProperties(createBookCommand,
        		bookCreatedEvent);
        AggregateLifecycle.apply(bookCreatedEvent);
    }
	@CommandHandler
	public void handle(DeleteBookCommand deleteBookCommand) {
		BookDeletedEvent event
				= new BookDeletedEvent();
		BeanUtils.copyProperties(deleteBookCommand,
				event);

		AggregateLifecycle.apply(event);
	}

	@EventSourcingHandler
    public void on(BookCreatedEvent event) {
		this.bookId = event.getBookId();
		this.author = event.getAuthor();
		this.isReady = event.getIsReady();
		this.name = event.getName();
    }

	@EventSourcingHandler
	public void on(BookUpdatedEvent event) {
		this.bookId = event.getBookId();
		this.author = event.getAuthor();
		this.name = event.getName();
		this.isReady = event.getIsReady();
	}
	@EventSourcingHandler
	public void on(BookDeletedEvent event) {
		this.bookId = event.getBookId();
	}

}
