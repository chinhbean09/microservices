package com.chinhbean.bookservice.command.api.controller;

import java.util.UUID;

import com.chinhbean.bookservice.command.api.command.DeleteBookCommand;
import com.chinhbean.bookservice.command.api.command.UpdateBookCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinhbean.bookservice.command.api.command.CreateBookCommand;
import com.chinhbean.bookservice.command.api.model.BookRequestModel;

@RestController
@RequestMapping("/api/v1/books")
public class BookCommandController {

	@Autowired
	private CommandGateway commandGateway;
	
	@PostMapping
	public String addBook(@RequestBody BookRequestModel model) {
		CreateBookCommand command = new CreateBookCommand(UUID.randomUUID().toString(),model.getName(), model.getAuthor(), true);
		commandGateway.sendAndWait(command);
		return "added Book";
	}

	@PutMapping
	public String updateBook(@RequestBody BookRequestModel model) {
		UpdateBookCommand command = new UpdateBookCommand(model.getBookId(), model.getName(), model.getAuthor(),model.getIsReady());
		commandGateway.sendAndWait(command);
		return "updated book";
	}
	@DeleteMapping("/{bookId}")
	public String deleteBook(@PathVariable String bookId) {
		commandGateway.sendAndWait(new DeleteBookCommand(bookId));
		return "deleted book";
	}
}
