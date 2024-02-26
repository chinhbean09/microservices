package com.chinhbean.bookservice.command.api.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String>{

}
