package com.tanthanh.borrowingservice.command.api.service;

import com.tanthanh.borrowingservice.command.api.model.Message;

public interface IBorrowService {
	void sendMessage(Message message);
	String findIdBorrowing(String employeeId,String bookId);
}
