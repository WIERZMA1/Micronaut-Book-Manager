package org.book.manager.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.transaction.annotation.Transactional;
import org.book.manager.domain.Book;

@Transactional
@Repository
public interface BookRepository extends JpaRepository<Book, String> {

}
