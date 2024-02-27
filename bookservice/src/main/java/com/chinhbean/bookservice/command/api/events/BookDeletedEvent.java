package com.chinhbean.bookservice.command.api.events;

public class BookDeletedEvent {
	private String bookId;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	
}
